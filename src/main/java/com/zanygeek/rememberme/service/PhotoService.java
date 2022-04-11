package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Obituary;
import com.zanygeek.rememberme.entity.Photo;
import com.zanygeek.rememberme.repository.MemorialRepository;
import com.zanygeek.rememberme.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {
    @Autowired
    UploadService uploadService;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    MemorialRepository memorialRepository;

    //사진 저장 이름 생성 메서드
    String createStoredName() {
        return UUID.randomUUID().toString();
    }

    //사진 저장 메서드
    public Photo savePhoto(MultipartFile file, Memorial memorial) throws IOException {
        Photo photo = new Photo();
        photo.setStoredName(createStoredName());
        photo.setUploadName(file.getOriginalFilename());
        photo.setMemorial(memorial);

        return upload(file,photo);
    }
    //여러 사진 저장 메서드
    public List<Photo> savePhotos(List<MultipartFile> files, int memorialId, Obituary obituary, String fromName) throws IOException {
        List<Photo> photos = new ArrayList<>();
        Memorial memorial = memorialRepository.findById(memorialId).get();
        for(MultipartFile file: files) {
            Photo photo = new Photo();
            photo.setStoredName(createStoredName());
            photo.setUploadName(file.getOriginalFilename());
            photo.setObituaryId(obituary.getId());
            photo.setMemorial(memorial);
            photo.setFromName(fromName);
            photos.add(upload(file,photo));
        }
        return photos;
    }

    //메인사진 저장 메서드
    public void saveMainPhoto(MultipartFile file, Memorial memorial) throws IOException {
        Photo photo = this.savePhoto(file, memorial);
        photo.setMain(true);

        upload(file, photo);
    }

    //사진 s3 업로드
    public Photo upload(MultipartFile file, Photo photo) throws IOException {
        String url = uploadService.upload(file, photo);
        photo.setUrl(url);

        return save(photo);
    }

    //사진 db 저장
    public Photo save(Photo photo){
        return photoRepository.save(photo);
    }

    public Photo getMainPhoto(Memorial memorial){
       return photoRepository.findByMemorialIdAndMainIsTrue(memorial.getId());
    }
}
