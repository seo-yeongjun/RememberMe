package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import com.zanygeek.rememberme.form.DisclosureForm;
import com.zanygeek.rememberme.form.EditMemorialsForm;
import com.zanygeek.rememberme.repository.MemorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemorialService {
    @Autowired
    MemorialRepository memorialRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PhotoService photoService;

    //기념관 저장, 암호가 있으면 해쉬 암호화
    public Memorial save(Memorial memorial, Member member) {
        memorial.setMemberId(member.getId());
        if (memorial.getLocked()) {
            memorial.setPassword(passwordEncoder.encode(memorial.getPassword()));
        }
        return memorialRepository.save(memorial);
    }

    public Memorial update(Memorial memorial) {
        return memorialRepository.save(memorial);
    }

    public Memorial update(Memorial memorial, DisclosureForm disclosureForm) {
        memorial.setLocked(disclosureForm.getLocked());
        memorial.setPassword(passwordEncoder.encode(disclosureForm.getPassword()));
        return memorialRepository.save(memorial);
    }

    public Memorial getMemorialById(int memorialId) {
        return memorialRepository.findById(memorialId).orElse(null);
    }

    public List<EditMemorialsForm> getMemorialsFormByMemberId(int memberId) {
        List<EditMemorialsForm> forms = new ArrayList<EditMemorialsForm>();
        List<Memorial> memorials = memorialRepository.findAllByMemberId(memberId);
        for (Memorial memorial : memorials) {
            EditMemorialsForm form = new EditMemorialsForm(memorial);
            form.setMainPhoto(photoService.getMainPhoto(memorial));
            forms.add(form);
        }
        return forms;
    }
    public void delete(Memorial memorial){
        List<Photo> photos = memorial.getPhoto();
        for(Photo photo : photos){
            photoService.deletePhotoByUrl(photo.getUrl());
        }
        memorialRepository.delete(memorial);
    }
}