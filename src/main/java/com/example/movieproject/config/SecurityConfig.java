package com.example.movieproject.config;

import com.example.movieproject.common.oauth2.handler.OAuth2FailHandler;
import com.example.movieproject.common.oauth2.handler.OAuth2SuccessHandler;
import com.example.movieproject.service.CustomOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2Service oauth2Service;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    private final OAuth2FailHandler oauth2FailHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated();

        http.oauth2Login()
                .userInfoEndpoint().userService(oauth2Service)
                .and()
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailHandler);
        return  http.build();
    }


}
