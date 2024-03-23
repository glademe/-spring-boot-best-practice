package com.harbor.validate.validate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.*;
import com.harbor.validate.validate.annotations.Enum;
import com.harbor.validate.validate.annotations.*;
import com.harbor.validate.validate.enums.ServiceCodeEnum;
import com.harbor.validate.validate.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author :Jooye
 * @date : 2024-03-23 21:24
 * @Describe: 类的描述信息
 */
@Slf4j
public class ValidateUtils {
    public static final String ORDER_NAME = "order";

    /**
     * 集合类型校验
     *
     * @param collection
     */
    public static void validateCollection(Collection<?> collection) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new ServiceException(ServiceCodeEnum.SYS_ERROR, "被校验对象不能为空");
        }
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            validate(next);
        }
    }

    public static void validate(Object o) {
        if (ObjectUtil.isNull(o)) {
            throw new ServiceException(ServiceCodeEnum.ILLEGAL_PARAM, "被校验对象不能为空");
        }
        if (o instanceof Collection) {
            validateCollection((Collection<?>) o);
        }
        Class<Object> aClass = ClassUtil.getClass(o);
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(Boolean.TRUE);

            try {
                Object targetValue = field.get(o);
                Annotation[] annotations = field.getAnnotations();
                List<Annotation> collect = Arrays.stream(annotations).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(collect)) {
                    return;
                }
                validate(targetValue, collect);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void validate(Object targetValue, List<Annotation> annotations) {
        annotations.removeIf(Objects::isNull);

        /**
         * 根据Order进行排序
         */
        annotations.sort(new Comparator<Annotation>() {
            @Override
            public int compare(Annotation o1, Annotation o2) {
                try {
                    Map<String, Object> annotationAttributes1 = AnnotationUtils.getAnnotationAttributes((Annotation) o1);
                    Map<String, Object> annotationAttributes2 = AnnotationUtils.getAnnotationAttributes((Annotation) o2);
                    if (annotationAttributes1.get(ORDER_NAME) instanceof Integer && annotationAttributes2.get(ORDER_NAME) instanceof Integer) {
                        int order1 = Integer.parseInt(annotationAttributes1.get(ORDER_NAME) + "");
                        int order2 = Integer.parseInt(annotationAttributes2.get(ORDER_NAME) + "");
                        return order1 - order2;
                    }
                } catch (Exception e) {
                    log.error("检验出错：", e);
                }
                return 0;
            }
        });

        /**
         * 对注解进行处理
         */
        annotations.forEach(a -> {
            if (a instanceof Between) {
                validateBetween(targetValue, (Between) a);
            }

            if (a instanceof BooleanState) {
                validateBooleanState(targetValue, (BooleanState) a);
            }

            if (a instanceof Email) {
                validateEmail(targetValue, (Email) a);
            }

            if (a instanceof Enum) {
                validateEnum(targetValue, (Enum) a);
            }

            if (a instanceof IdCard) {
                validateIdCard(targetValue, (IdCard) a);
            }

            if (a instanceof Length) {
                validateLength(targetValue, (Length) a);
            }

            if (a instanceof NotBlank) {
                validateNotBlank(targetValue, (NotBlank) a);
            }

            if (a instanceof NotNull) {
                validateNotNull(targetValue, (NotNull) a);
            }

            if (a instanceof Phone) {
                validatePhone(targetValue, (Phone) a);
            }

            if (a instanceof Regular) {
                validateRegular(targetValue, (Regular) a);
            }
        });

    }


    private static void validateRegular(Object targetValue, Regular a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }
        String regular = a.regular();
        boolean match = ReUtil.isMatch(regular, (CharSequence) targetValue);
        if (!match) {
            throw new ServiceException(a.code(), a.message());
        }
    }

    private static void validatePhone(Object targetValue, Phone a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }

        boolean isMobile = Validator.isMobile(targetValue + "");
        if (!isMobile) {
            throw new ServiceException(a.code(), a.message());
        }
    }

    private static void validateNotNull(Object targetValue, NotNull a) {
        if (ObjectUtil.isNull(targetValue)) {
            throw new ServiceException(a.code(), a.message());
        }
    }

    private static void validateNotBlank(Object targetValue, NotBlank a) {
        if (ObjectUtil.isNull(targetValue)) {
            throw new ServiceException(a.code(), a.message());
        }
    }

    private static void validateLength(Object targetValue, Length a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }
        int source = Integer.parseInt(targetValue + "");

        if (source < a.min() || source > a.max()) {
            String format = String.format(a.message(), a.min(), a.max());
            throw new ServiceException(a.code(), format);
        }
    }

    private static void validateIdCard(Object targetValue, IdCard a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }
        boolean isIdCard = IdcardUtil.isValidCard(targetValue + "");
        if (!isIdCard) {
            throw new ServiceException(a.code(), a.message());
        }

    }

    private static void validateEnum(Object targetValue, Enum a) {
        if (!a.canBeNull()) {
            if (Objects.isNull(a)) {
                throw new ServiceException(ServiceCodeEnum.ILLEGAL_PARAM, "参数不能为空");
            }
        }
        Class<? extends java.lang.Enum<?>> validate = a.validate();
        Method method = ReflectUtil.getMethod(validate, a.validateMethod());
        try {
            String voidName = "void";
            if (ObjectUtil.equal(method.getReturnType().getTypeName(), voidName)) {
                method.invoke(validate, targetValue);
            } else {
                Object invoke = method.invoke(validate, targetValue);
                if (ObjectUtil.isNull(invoke)) {
                    throw new ServiceException(ServiceCodeEnum.ILLEGAL_PARAM, a.message());
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("", e);
            throw new ServiceException(ServiceCodeEnum.SYS_ERROR, "类型验证失败");
        }
    }

    private static void validateEmail(Object targetValue, Email a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }
        boolean isEmail = Validator.isEmail(targetValue + "");
        if (!isEmail) {
            throw new ServiceException(a.code(), a.message());
        }

    }

    private static void validateBooleanState(Object targetValue, BooleanState a) {
        if (ObjectUtil.isNull(targetValue) && a.canBeNull()) {
            return;
        }

        if (!ObjectUtil.equal(targetValue, a.status())) {
            throw new ServiceException(a.code(), a.message());
        }

    }

    private static void validateBetween(Object targetValue, Between between) {
        if (ObjectUtil.isNull(targetValue) && between.canBeNull()) {
            return;
        }

        if (ObjectUtil.isNull(targetValue)) {
            throw new ServiceException(between.code(), "参数不能为空");
        }

        if (targetValue instanceof Number) {
            double source = Double.parseDouble(targetValue + "");
            if (source < between.min() || source > between.max()) {
                String message = String.format(between.message(), between.min(), between.max());
                throw new ServiceException(between.code(), message);
            }
        } else {
            throw new ServiceException(ServiceCodeEnum.SYS_ERROR, "待校验的类型必须为数字类型");
        }
    }

}
