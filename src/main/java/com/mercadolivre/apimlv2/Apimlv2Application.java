package com.mercadolivre.apimlv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Apimlv2Application {

    public static void main(String[] args) {
        SpringApplication.run(Apimlv2Application.class, args);
    }

}
