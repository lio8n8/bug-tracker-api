package com.app.bugtracker.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Contains swagger configuration.
 */
@Configuration
public class SwaggerConfigs {

    /**
     * Set configs.
     *
     * @return {@link Docket}.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.app.auth.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(new ArrayList<>(Arrays.asList(
                        new ApiKey("Bearer " + "%token", AUTHORIZATION, "Header"))));
    }

    /**
     * Set API info.
     *
     * @return {@link ApiInfo}.
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Auth service")
                .description("Bug tracker authentication and user registration service.")
                .version("0.0.1")
                .build();
    }
}
