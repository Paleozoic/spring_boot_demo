package com.maxplus1.demo.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket test1Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试1API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maxplus1.demo.rest.test1"))
                .paths(PathSelectors.any()).build();
    }
    @Bean
    public Docket test2Api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试2API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maxplus1.demo.rest.test2"))
                .paths(PathSelectors.any()).build();
    }


    /**
     * 项目API信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger测试项目API文档")
                .description("这只是测试~")
                .termsOfServiceUrl("maxplus1.com")
                .contact(new Contact("Paleo ", "http://maxplus1.com", "xiaolong.qiu@foxmail.com"))
                .version("1.0")
                .build();
    }
}
