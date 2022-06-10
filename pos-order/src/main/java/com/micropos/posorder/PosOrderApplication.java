package com.micropos.posorder;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class PosOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosOrderApplication.class, args);
    }

}
