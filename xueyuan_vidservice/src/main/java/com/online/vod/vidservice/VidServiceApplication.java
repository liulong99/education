package com.online.vod.vidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VidServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VidServiceApplication.class,args);
    }
}
