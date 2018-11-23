package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.demo"})
public class Application extends WebMvcConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
    }
}
