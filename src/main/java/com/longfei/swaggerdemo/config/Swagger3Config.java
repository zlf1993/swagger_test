package com.longfei.swaggerdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger3Config {

    @Value("${swaggerenable}")
    private Boolean swaggerenable;

    @Bean //config swagger bean
    public Docket docket(Environment environment){

        //獲取項目的環境
        Profiles profiles = Profiles.of("dev", "stg");
        //判斷是否處在自己設定的環境當中
        boolean b = environment.acceptsProfiles(profiles);

        //        在@Bean中不能使用@Value???
        //        //直接使用@Value
        //        @Value("${server.port}")
        //        private static String port;

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(swaggerenable)
                .select()
                //RequestHandlerSelectors,配置要掃描的接口方式
                //withClassAnnotation: 掃描類上的註解
                //withMethodAnnotation: 掃描方法上的註解
                .apis(RequestHandlerSelectors.basePackage("com.longfei.swaggerdemo"))
                .paths(PathSelectors.ant("/test"))
                .build();
    }

    //config aip info
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Homedepot OpenAPI Document")
                .description("test api UI")
                .contact(new Contact("longfei","https://philocampus.com", "longfei_zeng@homedepot.com"))
                .version("1.0")
                .build();
    }
}
