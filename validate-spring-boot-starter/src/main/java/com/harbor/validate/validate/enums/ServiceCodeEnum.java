package com.harbor.validate.validate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :Jooye
 * @date : 2024-03-23 21:07
 * @Describe: 类的描述信息
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public enum ServiceCodeEnum {

    SYS_ERROR(-1, "服务繁忙，请稍候再试"),
    ILLEGAL_PARAM(1, "参数错误");

    private Integer code;

    private String message;


    public static ServiceCodeEnum codeOf(Integer code) {
        for (ServiceCodeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
