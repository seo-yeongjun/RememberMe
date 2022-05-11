package com.zanygeek.rememberme.form;

import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EditMemorialsForm {
    private int id;
    private String name;
    private Boolean locked;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deathDate;
    private String password;
    private Photo mainPhoto;

    public EditMemorialsForm(Memorial memorial){
        this.id = memorial.getId();
        this.name = memorial.getName();
        this.locked = memorial.getLocked();
        this.password = memorial.getPassword();
        this.birthDate = memorial.getBirthDate();
        this.deathDate = memorial.getDeathDate();
    }
}
