package com.example.payment8001.controller;


import com.example.payment8001.common.CommonResult;
import com.example.payment8001.entity.Payment;
import com.example.payment8001.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    public CommonResult create(Payment payment) {
//
//    }


    @PostMapping("/payment/get/{id}")
    public CommonResult paymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("查询的结果" + paymentById);
        if (paymentById != null) {
            return new CommonResult(200, "查询成功" + serverPort, paymentById);
        } else {
            return new CommonResult(444, "查询失败" + serverPort, null);
        }
    }

}
