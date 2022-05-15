package com.zanygeek.rememberme.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class MemorialPassword {
    private int memorialId;
    private String password;

    public MemorialPassword(int memorialId){
        this.memorialId = memorialId;
    }
    public MemorialPassword(){

    }
}
