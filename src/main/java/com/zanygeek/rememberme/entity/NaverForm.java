package com.zanygeek.rememberme.entity;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class NaverForm {
    private String resultcode;
    private String message;
    private LinkedHashMap<String, String> response;

    public String getId() {
        return response.get("id");
    }

    public String getEmail() {
        return response.get("email");
    }

    public String getMobile() {
        return response.get("mobile");
    }

    public String getName() {
        return response.get("name");
    }
}
