package com.yunwuye.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.yunwuye.sample.configuration.DynamicDataSourceRegister;

@SpringBootApplication(scanBasePackages = { "com.yunwuye.sample" })
@MapperScan(value = { "com.yunwuye.sample.dal", "com.yunwuye.sample.dao.mapper" })
@EnableDubboConfiguration
@ConfigurationProperties(prefix = "dubbo.application")
@Import(DynamicDataSourceRegister.class)
public class Application {
    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }
}
