package com.example.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by jiemin on 2022/9/24 18:12
 */
@RestController
public class OrderConsulController {


    public static final String INVOKE_URL = "http://consul-provider-payment";
    @Resource
    private RestTemplate restTemplate;


    /**
     * http://localhost/consumer/payment/zk
     *
     * @return
     */
    @GetMapping("/consumer/payment/consul")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
    }


}
