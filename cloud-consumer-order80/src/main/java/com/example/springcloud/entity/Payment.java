package com.example.springcloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 支付表
 * </p>
 *
 * @author jiemin
 * @since 2022-09-02
 */

@Data
public class Payment implements Serializable {

    private static final long serialVersionUID = -798435010948786660L;


    private Long id;

    private String serial;


}
