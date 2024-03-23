package com.harbor.validate.validate.annotations;

import com.harbor.validate.validate.enums.ServiceCodeEnum;

import java.lang.annotation.*;

/**
 * @author :Jooye
 * @date : 2024-03-23 20:56
 * @Describe: 类的描述信息
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Between {

    /**
     * 排序：同一个字段，同时使用多个注解
     *
     * @return
     */
    int order() default 0;

    /**
     * 最小值
     *
     * @return
     */
    double min() default 0D;

    /**
     * 最小值
     *
     * @return
     */
    double max() default 0D;

    /**
     * 可以为空？的时候，则不做校验
     *
     * @return
     */
    boolean canBeNull() default false;

    /**
     * 异常状态
     *
     * @return
     */
    ServiceCodeEnum code() default ServiceCodeEnum.ILLEGAL_PARAM;


    String message() default "参数范围必须%s ~ %s";
}
