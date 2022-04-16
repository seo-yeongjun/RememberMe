package com.zanygeek.rememberme.form;

import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import lombok.Data;

@Data
public class EditMemorialsForm {
    private int id;
    private String name;
    private Boolean locked;
    private String password;
    private Photo mainPhoto;

    public EditMemorialsForm(Memorial memorial){
        this.id = memorial.getId();
        this.name = memorial.getName();
        this.locked = memorial.getLocked();
        this.password = memorial.getPassword();
    }
}
