package com.harbor.validate.validate;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.harbor.validate.validate.annotations.Trim;
import com.harbor.validate.validate.enums.ServiceCodeEnum;
import com.harbor.validate.validate.exception.ServiceException;

import java.lang.reflect.Field;

/**
 * @author :Jooye
 * @date : 2024-03-23 23:06
 * @Describe: 类的描述信息
 */
public class TrimUtils {

    public static void trim(Object o,boolean withAnnotation){
        if (ObjectUtil.isNull(o)){
            throw new ServiceException(ServiceCodeEnum.ILLEGAL_PARAM,"校验对象不能为空");
        }
        Trim trim = o.getClass().getAnnotation(Trim.class);
        if (withAnnotation && ObjectUtil.isNull(o)){
            return;
        }
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(Boolean.TRUE);
            Class<?> type = field.getType();
            if (ObjectUtil.equal(type,String.class)){
                try {
                    String value = (String) field.get(o);
                    if (ObjectUtil.isNull(value)){
                        continue;
                    }
                    field.set(o,value.trim());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
