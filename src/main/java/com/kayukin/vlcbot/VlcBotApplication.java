package com.kayukin.vlcbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VlcBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(VlcBotApplication.class, args);
    }
}
