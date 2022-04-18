package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "memorial")
public class Memorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deathDate;
    private Boolean locked;
    private String password;
    private int memberId;
    @OneToMany(mappedBy = "memorial")
    private List<Photo> photo;
    private String mainText;
}
