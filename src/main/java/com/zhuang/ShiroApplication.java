package com.zhuang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
@MapperScan("com.zhuang.mapper")
public class ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
    }

}
