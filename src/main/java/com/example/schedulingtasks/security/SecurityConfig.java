package com.example.schedulingtasks.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Base64;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityFilter securityFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
        http
                .csrf()
                .disable();

    }

//    @Bean
//    public Base64.Encoder encoder() {
//        return Base64.getEncoder();
//    }

}
