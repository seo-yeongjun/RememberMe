package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "wreath")
public class Wreath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memorialId;
    @Size(max = 50)
    private String text;
    private String name;
    private String relation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotBlank
    private String password;
}
