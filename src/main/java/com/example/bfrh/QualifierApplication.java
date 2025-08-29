package com.example.bfrh;

import com.example.bfrh.service.QualifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class QualifierApplication implements CommandLineRunner {

    @Autowired
    private QualifierService service;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(QualifierApplication.class, args);
    }

    @Override
    public void run(String... args) {
        service.executeOnceOnStartup(
                env.getProperty("bfrh.name"),
                env.getProperty("bfrh.regNo"),
                env.getProperty("bfrh.email")
        );
    }
}
