package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.XY;
import com.zanygeek.rememberme.form.XYForm;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class AddressApiServiceTest {
    @Autowired
    AddressApiService addressApiService;
    @Test
    void getXY() {
        XY xy = addressApiService.getXY("경기도 성남시 분당구 불정로 6 그린팩토리");
        System.out.println(xy.getX()+" "+xy.getY());
    }
}