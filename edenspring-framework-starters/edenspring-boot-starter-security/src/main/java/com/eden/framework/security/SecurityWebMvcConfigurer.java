package com.eden.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityWebMvcConfigurer implements WebMvcConfigurer {

    private final AuthUserInterceptor authUserInterceptor;

    private final PermissionInterceptor permissionInterceptor;

    private final ObjectMapper objectMapper;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationUserIdMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authUserInterceptor).addPathPatterns("/**");
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**");
    }

    @Bean
    public AuthUserIdMethodArgumentResolver authenticationUserIdMethodArgumentResolver() {
        return new AuthUserIdMethodArgumentResolver(objectMapper);
    }

}