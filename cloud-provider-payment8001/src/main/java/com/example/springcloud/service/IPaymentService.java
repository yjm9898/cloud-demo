package com.example.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springcloud.entity.Payment;

/**
 * <p>
 * 支付表 服务类
 * </p>
 *
 * @author jiemin
 * @since 2022-09-02
 */
public interface IPaymentService extends IService<Payment> {

    Payment getPaymentById(Long id);

}
