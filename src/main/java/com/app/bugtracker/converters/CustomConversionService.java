package com.app.bugtracker.converters;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class CustomConversionService {
    
    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();

        Set<Converter> converters = new HashSet<>();
        converters.add(new UserConverter());
        converters.add(new TaskConverter());
        factoryBean.setConverters(converters);

        return factoryBean;
    }
}
