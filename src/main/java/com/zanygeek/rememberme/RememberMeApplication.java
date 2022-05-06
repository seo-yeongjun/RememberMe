package com.zanygeek.rememberme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RememberMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RememberMeApplication.class, args);
    }

}
