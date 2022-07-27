package com.ray.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class StructWebBootstrap {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder(StructWebBootstrap.class)
                .build();
        springApplication.run(args);
        log.info("=============================spring boot start successful !=============================");
    }
}
