package com.ccssoft.cloudauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //配置了swagger的docket的bean实例
    @Bean
    public Docket docket () {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo ())
//                .enable(true)//默认是true，是否开启swagger
                .select()
//                //RequestHandlerSelectors,配置要扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.ccssoft.cloudauth.controller"))
                .paths(PathSelectors.ant("/consumer/**"))//过滤路径
                .build();
    }
    private ApiInfo apiInfo () {
        //作者信息
        Contact contact = new Contact("moriarty","http://www.codermoriarty.top/","sherlock0909@icloud.com");
        return new ApiInfo(
                "无人机云平台API文档",
                "跳转接口详情展示",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }


}
