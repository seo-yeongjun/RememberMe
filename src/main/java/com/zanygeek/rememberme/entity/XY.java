package com.zanygeek.rememberme.entity;

import lombok.Data;

@Data
public class XY {
    private String x;
    private String y;

    public XY(String x, String y){
        this.x = x;
        this.y = y;
    }
}
