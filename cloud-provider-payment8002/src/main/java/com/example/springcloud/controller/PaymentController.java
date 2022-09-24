package com.example.springcloud.controller;


import com.example.springcloud.entity.CommonResult;
import com.example.springcloud.entity.Payment;
import com.example.springcloud.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 支付表 前端控制器
 * </p>
 *
 * @author jiemin
 * @since 2022-09-02
 */
@RestController
@Slf4j
public class PaymentController {


    @Autowired
    private IPaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        paymentService.save(payment);
        return new CommonResult(200, "新建成功" + serverPort, null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult paymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("查询的结果" + paymentById);
        if (paymentById != null) {
            return new CommonResult(200, "查询成功" + serverPort, paymentById);
        } else {
            return new CommonResult(444, "查询失败" + serverPort, null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();

        services.forEach(x -> {
            log.info("******element****" + x);
        });

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + instance.getUri());
        }

        return this.discoveryClient;
    }

}
