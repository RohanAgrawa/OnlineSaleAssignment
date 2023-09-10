package com.example.onlinesaledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class OnlineSaleDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineSaleDemoApplication.class, args);
    }

}
