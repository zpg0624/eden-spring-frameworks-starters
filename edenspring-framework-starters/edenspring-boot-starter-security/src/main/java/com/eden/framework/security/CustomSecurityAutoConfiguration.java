package com.eden.framework.security;

import com.eden.framework.context.AccessTypeHeaderMapper;
import com.eden.framework.context.PermissionProvider;
import com.eden.framework.context.ResourceAccessProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration
public class CustomSecurityAutoConfiguration {

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Bean
    public AuthUserInterceptor authUserInterceptor(PermissionProvider permissionProvider,
                                                   ResourceAccessProvider resourceAccessProvider,
                                                   AccessTypeHeaderMapper accessTypeHeaderMapper) {
        return new AuthUserInterceptor(permissionProvider, resourceAccessProvider, accessTypeHeaderMapper);
    }

    @ConditionalOnMissingBean
    @Bean
    public AccessTypeHeaderMapper accessTypeHeaderMapper() {
        return new AccessTypeHeaderMapper();
    }

    @ConditionalOnMissingBean
    @Bean
    public PermissionProvider permissionProvider() {
        return __ -> null;
    }

    @ConditionalOnMissingBean
    @Bean
    public ResourceAccessProvider resourceAccessProvider() {
        return __ -> null;
    }


}