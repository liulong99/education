package com.online.edu.ucservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }
//    @Bean
//    public Docket adminApiConfig(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("adminApi")
//                .apiInfo(adminApiInfo())
//                .select()
//                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
//                .build();
//    }
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("网站-用户管理API文档")
                .description("本文档描述了用户管理微服务接口定义")
                .version("1.0")
                .contact(new Contact("liulong", "https://home.cnblogs.com/u/liulong99/", "706750966@qq.com"))
                .build();
    }
//    private ApiInfo adminApiInfo(){
//        return new ApiInfoBuilder()
//                .title("后台管理系统-课程中心API文档")
//                .description("本文档描述了后台管理系统课程中心微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))
//                .build();
//    }
//
//}
}
