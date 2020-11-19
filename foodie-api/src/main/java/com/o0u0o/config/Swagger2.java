package com.o0u0o.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置
 * @author o0u0o
 * @date 2020/10/27 10:24 上午
 */

@Configuration
//开始swagger配置
@EnableSwagger2
public class Swagger2 {

    /**
     * 配置swagger2 核心配置 docket
     * @return
     */
    @Bean
    public Docket createRestApi(){
        //指定API类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                //用于定义api文档汇总信息
                .apiInfo(apiInfo())
                .select()
                //指定Controller包
                .apis(RequestHandlerSelectors.basePackage("com.o0u0o.controller"))
                //所有的Controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //文档页标题
                .title("天天吃货 电商平台API")
                .contact(new Contact("o0u0o",
                        "https://www.o0u0o.com",
                        "jobs@techgeng.com"))   //联系人信息
                .description("专为天天吃货提供的API文档")  //详细描述
                .version("1.0.1")   //文档版本号
                .termsOfServiceUrl("https://www.o0u0o.com") //网站地址
                .build();
    }
}
