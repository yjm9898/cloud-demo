package com.example.payment8001.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Payment对象", description = "支付表")
@Data
public class Payment implements Serializable {

    private static final long serialVersionUID = -798435010948786660L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("支付流水号")
    private String serial;


}
