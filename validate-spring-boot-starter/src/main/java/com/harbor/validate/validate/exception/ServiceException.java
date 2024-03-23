package com.harbor.validate.validate.exception;

import com.harbor.validate.validate.enums.ServiceCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author :Jooye
 * @date : 2024-03-23 21:26
 * @Describe: 类的描述信息
 */
@ToString
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceException extends RuntimeException {
    private Integer code;

    private String message;


    public ServiceException(ServiceCodeEnum serviceCodeEnum) {
        this.code = serviceCodeEnum.getCode();
        this.message = serviceCodeEnum.getMessage();
    }

    public ServiceException(ServiceCodeEnum serviceCodeEnum, String message) {
        this.code = serviceCodeEnum.getCode();
        this.message = message;
    }

}
