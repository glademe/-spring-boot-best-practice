package com.harbor.validate.validate.annotations;

import com.harbor.validate.validate.enums.ServiceCodeEnum;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 21:01
 * @Describe: 类的描述信息
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Regular {

    /**
     * 排序:同一个字段，同时使用多个注解
     */
    int order() default 0;

    /**
     * 正则表达式
     */
    String regular() default "";


    /**
     * 可以为空?为空的时候，则不做校验
     */
    boolean canBeNull() default false;

    /**
     * 异常状态
     */
    ServiceCodeEnum code() default ServiceCodeEnum.ILLEGAL_PARAM;


    /**
     * 自定义消息，如果没有自定义消息，则默认取code对应的。
     *
     * @return
     */
    String message() default "参数格式错误";
}
