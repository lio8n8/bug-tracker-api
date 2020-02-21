package com.app.bugtracker.configs;

import com.app.bugtracker.filters.JwtTokenAuthenticationFilter;
import com.app.bugtracker.users.services.CustomUserDetailsService;
import com.app.bugtracker.auth.services.ITokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.app.bugtracker.Urls.TOKENS;
import static com.app.bugtracker.Urls.USERS;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

/**
 * Contains security configuration.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

    private final ITokensService tokensService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfigs(final ITokensService tokensService,
                           final CustomUserDetailsService customUserDetailsService) {
        this.tokensService = tokensService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected final void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(OPTIONS, "/**").permitAll()
                .antMatchers(POST, TOKENS).permitAll()
                .antMatchers(POST, USERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        new JwtTokenAuthenticationFilter(
                                tokensService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Override
    public final void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs")
                .antMatchers(OPTIONS, "/**")
                .antMatchers(POST, TOKENS)
                .antMatchers(POST, USERS)
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-resources/**")
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
