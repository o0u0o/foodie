package com.o0u0o;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = "com.o0u0o.mapper")
@ComponentScan(basePackages = {"com.o0u0o", "org.n3r.idworker"})
public class FoodieCasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodieCasApplication.class, args);
    }

}
