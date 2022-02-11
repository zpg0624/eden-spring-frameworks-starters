package com.eden.framework.context;

import java.lang.annotation.*;

/**
 * @author eden
 * @since 2020/8/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnPermission {

    String[] value();

    String description() default "";

}
