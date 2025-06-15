package com.ifmg.ceamec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CeamecApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(CeamecApplication.class)
                .headless(false)
                .run(args);
    }

}
