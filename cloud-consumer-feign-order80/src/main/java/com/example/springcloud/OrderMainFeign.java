package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by jiemin on 2022/9/27 16:51
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderMainFeign {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainFeign.class, args);
    }

}
