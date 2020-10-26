package com.o0u0o;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author o0u0o
 * @Date 2020/5/12 10:54 下午
 * @Descripton: 启动类
 *
 * 注解@MapperScan 扫描 通用mapper所在的包
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.o0u0o.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
