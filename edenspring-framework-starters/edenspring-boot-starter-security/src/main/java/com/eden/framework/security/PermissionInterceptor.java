package com.eden.framework.security;

import com.eden.framework.context.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author eden
 * @since 2020/8/1
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.of(handler)
                .filter(HandlerMethod.class::isInstance)
                .map(HandlerMethod.class::cast)
                .map(handlerMethod -> handlerMethod.getMethodAnnotation(OnPermission.class))
                .map(OnPermission::value)
                .ifPresent(this::check);
        return true;
    }

    private void check(String[] permissions) {
        CurrentAuthUser authUser =
                Optional.ofNullable(AuthUserContext.current())
                        .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_LOGIN_MESSAGE));

        if (!authUser.hasPermission(permissions)) {
            throw new AuthException(AuthErrorCode.NO_PERMISSION);
        }
    }

}
