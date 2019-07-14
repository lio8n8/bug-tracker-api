package com.app.bugtracker.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.bugtracker.constants.Urls;
import com.app.bugtracker.filters.JwtTokenAuthenticationFilter;
import com.app.bugtracker.services.auth.CustomUserDetailsService;
import com.app.bugtracker.services.auth.IJwtTokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfigs extends WebSecurityConfigurerAdapter {
    private IJwtTokenService jwtTokenService;
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    public SecurityConfigs(IJwtTokenService jwtTokenService,
            CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Override
    protected final void configure(final HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.POST, Urls.USERS).permitAll()
            .antMatchers(HttpMethod.POST, Urls.Auth).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                    new JwtTokenAuthenticationFilter(
                            jwtTokenService
                    ),
                    UsernamePasswordAuthenticationFilter.class
            );
    }
    
    @Override
    public final void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/v2/api-docs")
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers(HttpMethod.POST, Urls.USERS)
            .antMatchers(HttpMethod.POST, Urls.Auth)
            .antMatchers("/swagger-ui.html")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/configuration/**")
            .antMatchers("/webjars/**");
    }
    
    @Override
    protected void configure(final AuthenticationManagerBuilder authManager) throws Exception {
        authManager.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        super.configure(authManager);
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
