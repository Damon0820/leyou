package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LyUploadService {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadService.class, args);
    }
}
