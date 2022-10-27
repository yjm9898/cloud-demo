package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by jiemin on 2022/10/27 10:23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9001 {

    /**
     * 主启动
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9001.class, args);
    }

}
