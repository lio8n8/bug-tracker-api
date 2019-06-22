package com.app.bugtracker.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.bugtracker.constants.Urls;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {
    @Override
    protected final void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(HttpMethod.POST, Urls.USERS).permitAll()
            .antMatchers(HttpMethod.PUT, Urls.USERS + "/**").permitAll();
    }
    
    @Override
    public final void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, Urls.USERS);
        web.ignoring().antMatchers(HttpMethod.PUT, Urls.USERS + "/**");
        web.ignoring().antMatchers("/swagger-ui.html");
    }
    
    @Override
    protected void configure(final AuthenticationManagerBuilder authManager) throws Exception {
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
