package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.entity.AddressForm;
import com.zanygeek.rememberme.service.AddressApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    @Autowired
    AddressApiService addressApiService;

    @GetMapping("/address/{keyword}")
    public AddressForm goBookPage(@PathVariable("keyword") String keyword) {
        return addressApiService.requestBook(keyword);
    }
}
