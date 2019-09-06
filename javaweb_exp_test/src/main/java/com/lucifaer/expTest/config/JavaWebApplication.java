package com.lucifaer.expTest.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.lucifaer.expTest.*"})
public class JavaWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaWebApplication.class, args);
    }
}
