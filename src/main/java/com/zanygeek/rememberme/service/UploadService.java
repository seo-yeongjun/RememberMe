package com.zanygeek.rememberme.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

//사진 업로드를 위한 서비스
@Service
@Log4j2
public class UploadService {
    @Autowired
    AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${localDir}")
    public String dir;

    public String upload(MultipartFile multipartFile, Photo photo) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, photo);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, Photo photo) {
        String fileName = photo.getMemorial().getMemberId() + "/" + photo.getMemorial().getId() + "/" + photo.getStoredName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(dir + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    //파일 삭제
    public boolean deleteFile(String url) {
        try {
            amazonS3Client.deleteObject(bucket, url.substring(url.indexOf(".com/") + 5));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}