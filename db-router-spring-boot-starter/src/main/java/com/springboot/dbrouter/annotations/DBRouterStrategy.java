package com.springboot.dbrouter.annotations;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-24 18:34
 * @Describe: 类的描述信息
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouterStrategy {

    /**
     * 是否分表 默认为false
     * @return
     */
    boolean splitTable() default false;
}
