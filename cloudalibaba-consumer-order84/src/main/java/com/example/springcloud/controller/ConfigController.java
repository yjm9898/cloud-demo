package com.example.springcloud.controller;

import com.example.springcloud.entity.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiemin on 2022/10/28 14:26
 */
@RestController
public class ConfigController {


    @Value("${config.name}")
    private String configName;

    @GetMapping("/config")
    public CommonResult<String> getConfigName() {
        return new CommonResult<>(200, configName);
    }

}
