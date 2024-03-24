package com.springboot.dbrouter.annotations;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-24 18:33
 * @Describe: 类的描述信息
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {
    /** 分库分表字段 */
    String key() default "";
}
