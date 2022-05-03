package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.AddressForm;
import com.zanygeek.rememberme.entity.XY;
import com.zanygeek.rememberme.form.XYForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AddressApiService {
    @Autowired
    RestTemplate restTemplate;


    public AddressForm requestBook(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        String CLIENT_ID = "yTZEf3uCoJNVJdgNem4b";
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        String CLIENT_SECRET = "ayGD_iRZkj";
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String naverApiAdress = "https://openapi.naver.com/v1/search/local.json?query={keyword}&display=20";
        return restTemplate.exchange(naverApiAdress, HttpMethod.GET, entity, AddressForm.class, keyword).getBody();
    }

    public XY getXY(String keyword) {
        HttpHeaders headers = new HttpHeaders();

        String CLIENT_ID = "lhozmkgpcf";
        headers.set("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        String CLIENT_SECRET = "zUtpcfYDcW3jyEBSLVSLNjFKk7Xs8n8cpQM4Ghsn";
        headers.set("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String naverApiAdress = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query={keyword}";
      XYForm xyForm = restTemplate.exchange(naverApiAdress, HttpMethod.GET, entity, XYForm.class, keyword).getBody();
        return new XY(xyForm.getX(),xyForm.getY());
    }
}

