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
public @interface Phone {
    /**
     * 排序
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
    String message() default "手机号码格式错误";
}
