package com.zanygeek.rememberme.form;

import com.zanygeek.rememberme.entity.Obituary;
import com.zanygeek.rememberme.entity.Photo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class ObituaryForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memorialId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private String text;
    private String relation;
    private String name;
    private String password;
    private Photo photo;

    public ObituaryForm(Obituary obituary){
        this.date = obituary.getDate();
        this.memorialId = obituary.getMemorialId();
        this.id = obituary.getId();
        this.name = obituary.getName();
        this.relation = obituary.getRelation();
        this.password = obituary.getPassword();
        this.text = obituary.getText();
    }
}
