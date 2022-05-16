package com.zanygeek.rememberme.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "unsubscribe")
public class Unsubscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memberId;
    private String token;

    public Unsubscribe(int memberId) {
        this.memberId = memberId;
        token = UUID.randomUUID().toString();
    }

    public Unsubscribe() {
    }
}
