package com.basaki.rules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.basaki.rules"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}