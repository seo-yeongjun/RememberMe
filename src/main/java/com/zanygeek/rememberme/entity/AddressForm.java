package com.zanygeek.rememberme.entity;

import lombok.Data;

@Data
public class AddressForm {
    private int display;
    private Item[] items;

    @Data
    static class Item {
        private String title;    //상호명
        private String description;  // 설명
        private String address; // 주소
    }

}