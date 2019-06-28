package com.app.bugtracker.configs;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigs {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors
            .basePackage("com.app.bugtracker.controllers"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securitySchemes(new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token", "Authorization", "Header"))));
    }
    
    /**
     * Api info
     * 
     * @return {@link ApiInfo}
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Bug Tracker Api")
            .description("Bug tracker api")
            .version("0.0.1")
            .build();
    }
}
