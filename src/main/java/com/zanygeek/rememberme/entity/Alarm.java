package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memorialId;
    private boolean checkEvent;
    private boolean checkDate;
    private int memberId;
}
