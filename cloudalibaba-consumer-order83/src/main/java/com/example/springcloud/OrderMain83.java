package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by jiemin on 2022/10/27 11:12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OrderMain83 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain83.class, args);
    }

}
