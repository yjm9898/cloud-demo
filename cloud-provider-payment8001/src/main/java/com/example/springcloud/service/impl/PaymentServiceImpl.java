package com.example.payment8001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.Payment;
import com.example.payment8001.mapper.PaymentMapper;
import com.example.payment8001.service.IPaymentService;
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
