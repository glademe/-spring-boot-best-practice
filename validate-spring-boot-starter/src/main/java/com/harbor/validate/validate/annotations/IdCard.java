package com.harbor.validate.validate.annotations;

import com.harbor.validate.validate.enums.ServiceCodeEnum;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 20:58
 * @Describe: 类的描述信息
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IdCard {
    /**
     * 排序:同一个字段，同时使用多个注解
     */
    int order() default 0;

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
     */
    String message() default "身份证号码格式错误";
}
