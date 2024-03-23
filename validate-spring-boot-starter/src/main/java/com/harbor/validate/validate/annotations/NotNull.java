package com.harbor.validate.validate.annotations;

import com.harbor.validate.validate.enums.ServiceCodeEnum;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 20:59
 * @Describe: 类的描述信息
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotNull {
    /**
     * 排序
     */
    int order() default 0;

    /**
     * 异常状态
     */
    ServiceCodeEnum code() default ServiceCodeEnum.ILLEGAL_PARAM;


    /**
     * 自定义消息，如果没有自定义消息，则默认取code对应的。
     */
    String message() default "参数不能为空";
}
