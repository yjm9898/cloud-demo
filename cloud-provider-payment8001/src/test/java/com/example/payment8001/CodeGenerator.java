package com.example.payment8001;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * Created by jiemin on 2022/8/29 11:21
 */
public class CodeGenerator {


    public static void main(String[] args) {
        FastAutoGenerator.create(new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/quickbi?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8",
                        "root", "pass1234").schema("quickbi"))
                .globalConfig(builder -> {
                    builder.author("jiemin") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("C:\\Users\\jiemin\\Desktop\\code-gen"); // 指定输出目录
                })
                .packageConfig(builder -> builder.parent("com.example.payment8001"))
                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("请输入表名，多个表名用,隔开")))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
