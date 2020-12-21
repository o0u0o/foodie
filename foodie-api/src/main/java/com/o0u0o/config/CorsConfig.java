package com.o0u0o.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * @author mac
 * @date 2020/11/19 10:38 上午
 */
@Configuration
public class CorsConfig {

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        //1. 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://foodie.laitou.info");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:63344");
        config.addAllowedOrigin("http://localhost:63343");

        //1.2. 设置是否发送cookie信息
        config.setAllowCredentials(true);

        //1.3. 设置方向的请求方式 GET POST ...
        config.addAllowedMethod("*");

        //1.4. 设置运行的header
        config.addAllowedHeader("*");

        //2.1 为url添加路径映射
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        //3.返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }


}
