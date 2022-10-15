package com.example.springcloud.controller;

import com.example.springcloud.entity.CommonResult;
import com.example.springcloud.service.PaymentFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by jiemin on 2022/10/13 10:33
 */
@RestController
public class OrderController {

    @Resource
    PaymentFeign paymentFeign;


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    CommonResult<String> paymentInfo_OK(@PathVariable("id") Integer id) {
        String res = paymentFeign.paymentInfo_OK(id);
        return new CommonResult<>(200, res);
    }


    /**
     * 降级，服务调用者
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    CommonResult<String> paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String res = paymentFeign.paymentInfo_TimeOut(id);
        return new CommonResult<>(200, res);
    }


//    public CommonResult<String> paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
//        return new CommonResult<>(200, "我是消费者80,对方支付系统繁忙请10秒种后再试或者自己运行出错请检查自己,o(╥﹏╥)o");
//    }


}
