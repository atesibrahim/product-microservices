package com.pcomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import static springfox.documentation.builders.PathSelectors.any;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaServer
public class ProductServiceApplication {

    @Bean
    public Docket apiProduct(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("product").select()
                .apis(RequestHandlerSelectors.basePackage("com.pcomm.product"))
                .paths(any()).build().apiInfo(ApiInfo.DEFAULT);
    }

    @Bean
    public Docket apiBrand(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("brand").select()
                .apis(RequestHandlerSelectors.basePackage("com.pcomm.brand"))
                .paths(any()).build().apiInfo(ApiInfo.DEFAULT);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }





}
