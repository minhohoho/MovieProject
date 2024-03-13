package com.example.movieproject.config;

import com.example.movieproject.common.jwt.JwtAuthenticationFilter;
import com.example.movieproject.common.jwt.JwtProvider;
import com.example.movieproject.common.jwt.handler.JwtAccessDeniedHandler;
import com.example.movieproject.common.jwt.handler.JwtAuthenticationEntryPoint;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2Service oauth2Service;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    private final OAuth2FailHandler oauth2FailHandler;
    private final JwtProvider jwtProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .formLogin().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);


        http.authorizeRequests()
                        .anyRequest()
                .permitAll();

        http.oauth2Login()
                .userInfoEndpoint().userService(oauth2Service)
                .and()
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailHandler);
        return  http.build();
    }


}
