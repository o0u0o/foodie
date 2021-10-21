package com.o0u0o;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 * @Author o0u0o
 * @Date 2020/5/12 10:54 下午
 * @Descripton: 启动类
 *
 * 注解@MapperScan 扫描 通用mapper所在的包
 * exclude 排除到自动装配之外 SecurityAutoConfiguration.class} 不需要每次请求都需要SpringSecurity密码
 **/
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.o0u0o.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"com.o0u0o", "org.n3r.idworker"})
//开始基于redis作为spring session
@EnableRedisHttpSession
// 开启定时任务
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
