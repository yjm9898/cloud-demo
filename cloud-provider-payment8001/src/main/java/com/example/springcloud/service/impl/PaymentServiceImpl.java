package com.example.springcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springcloud.entity.Payment;
import com.example.springcloud.mapper.PaymentMapper;
import com.example.springcloud.service.IPaymentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付表 服务实现类
 * </p>
 *
 * @author jiemin
 * @since 2022-09-02
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {

    @Override
    public Payment getPaymentById(Long id) {
        return this.getById(id);
    }
}
