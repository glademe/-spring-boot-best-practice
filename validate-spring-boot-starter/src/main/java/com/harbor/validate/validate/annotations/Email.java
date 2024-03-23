package com.harbor.validate.validate.annotations;

import com.harbor.validate.validate.enums.ServiceCodeEnum;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 20:57
 * @Describe: 类的描述信息
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Email {

    int order() default 0;

    boolean canBeNull() default false;

    ServiceCodeEnum code() default ServiceCodeEnum.ILLEGAL_PARAM;

    String message() default "邮箱格式错误";
}
