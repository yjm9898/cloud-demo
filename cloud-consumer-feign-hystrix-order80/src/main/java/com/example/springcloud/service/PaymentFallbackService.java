package com.example.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * Created by jiemin on 2022/10/15 10:58
 */
@Component
public class PaymentFallbackService implements PaymentFeign{

    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService fall back paymentInfo_OK, 笑脸";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService fall back paymentInfo_TimeOut, 笑脸";
    }
}
