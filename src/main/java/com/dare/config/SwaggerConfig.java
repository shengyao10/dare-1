package com.dare.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author: shengyao
 * @Package: com.deer.config
 * @Date: 2020/8/3 23:04
 * @Description:
 * @version: 1.0
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    // 配置了Swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 配置要扫描接口的方式
                // basePackage指定要扫描的包
//                 any()扫描全部
                // none()都不扫描
                // withClassAnnotation()扫描类上的注解
                // withMethodAnnotation()扫描方法上的注解
                .apis(RequestHandlerSelectors.any())
                // paths()过滤什么路径
//                .paths(PathSelectors.ant())
                .build();
    }

    // 配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){

        // 作者信息
        Contact contact = new Contact("德尔小组", "", "");

        return new ApiInfo(
                "Api文档",
                "德尔小组",
                "1.0",
                "",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
