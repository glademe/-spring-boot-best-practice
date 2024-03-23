package com.harbor.validate.validate.annotations;

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
public @interface Trim {
}
