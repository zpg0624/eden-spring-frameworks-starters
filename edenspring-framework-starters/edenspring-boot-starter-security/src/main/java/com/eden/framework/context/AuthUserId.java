package com.eden.framework.context;

import java.lang.annotation.*;

/**
 * 应用在控制器方法参数上，用来获取登陆用户ID
 *
 * @author eden
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUserId {}
