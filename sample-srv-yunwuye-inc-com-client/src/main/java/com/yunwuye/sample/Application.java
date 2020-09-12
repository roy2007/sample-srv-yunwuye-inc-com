package com.yunwuye.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"com.yunwuye.sample",""})
public class Application {
    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }
}
