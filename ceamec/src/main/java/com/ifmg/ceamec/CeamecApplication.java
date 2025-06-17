package com.ifmg.ceamec;

import com.ifmg.ceamec.view.TelaLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CeamecApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(CeamecApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

}
