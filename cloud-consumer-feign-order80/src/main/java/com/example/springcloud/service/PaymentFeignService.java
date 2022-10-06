package com.example.springcloud.service;

import com.example.springcloud.entity.CommonResult;
import com.example.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by jiemin on 2022/9/27 17:25
 */

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE") //目标微服务的名称
public interface PaymentFeignService {

    //Feign接口中的方法要与目标服务中的Controller中的方法完全一致
    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeout();

}
