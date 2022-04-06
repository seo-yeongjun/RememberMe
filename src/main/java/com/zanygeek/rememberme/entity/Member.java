package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;
    private String password;
    private String name;
    private String email;
    private Date date;
    private String phoneNumber;
    private Boolean enabled;
}
