package com.app.bugtracker.configs;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfigs {

    @Bean
    public Validator localValidatorFactoryBean (){
        return new LocalValidatorFactoryBean();
    }
}
