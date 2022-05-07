package com.zanygeek.rememberme.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memorialId;
    private String text;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String time;
}
