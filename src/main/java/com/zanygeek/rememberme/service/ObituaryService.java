package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Obituary;
import com.zanygeek.rememberme.form.ObituaryForm;
import com.zanygeek.rememberme.repository.ObituaryRepository;
import com.zanygeek.rememberme.repository.PhotoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ObituaryService {
    @Autowired
    ObituaryRepository obituaryRepository;
    @Autowired
    PhotoRepository photoRepository;

    public Obituary saveObituary(Obituary obituary, int memorialId){
        obituary.setMemorialId(memorialId);
        obituary.setDate(LocalDateTime.now());
        obituary.setText(obituary.getText().replaceAll("\r\n","<br>"));
        try{
        return obituaryRepository.save(obituary);}
        catch (Exception e){
            log.error("에러발생 :"+e);
        }
        return null;
    }

    public List<ObituaryForm> getObituaryForms(int memorialId){
        List<Obituary> obituaries = obituaryRepository.findAllByMemorialIdOrderByIdDesc(memorialId);
        List<ObituaryForm> obituaryForms = new ArrayList<>();
        for(Obituary obituary : obituaries){
            ObituaryForm obituaryForm = new ObituaryForm(obituary);
            obituaryForm.setPhoto(photoRepository.findAllByObituaryId(obituary.getId()));
            obituaryForms.add(obituaryForm);
        }
        return obituaryForms;
    }
}
