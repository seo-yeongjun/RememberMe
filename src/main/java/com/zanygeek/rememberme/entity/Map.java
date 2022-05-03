package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String x;
    private String y;
    private String address;
    private String name;
    private String title;
    private int memorialId;
    private String description;
}
