package com.josdem.gmail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GmailSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmailSpringBootApplication.class, args);
    }

}
