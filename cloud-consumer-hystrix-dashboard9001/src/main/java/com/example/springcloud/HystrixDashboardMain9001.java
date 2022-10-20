package com.example.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by jiemin on 2022/10/18 10:22
 */
@EnableHystrixDashboard
@SpringBootApplication
@EnableEurekaClient
public class HystrixDashboardMain9001 {

    /**
     * 主启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain9001.class, args);
    }

}
