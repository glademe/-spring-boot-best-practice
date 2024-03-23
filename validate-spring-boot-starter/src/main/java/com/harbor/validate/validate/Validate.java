package com.harbor.validate.validate;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 20:54
 * @Describe: 自定义验证注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
}
