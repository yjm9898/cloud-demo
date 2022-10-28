package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by jiemin on 2022/10/28 14:26
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OrderMain84 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain84.class, args);
    }

}
