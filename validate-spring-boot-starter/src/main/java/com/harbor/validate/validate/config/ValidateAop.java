package com.harbor.validate.validate.config;

import cn.hutool.core.util.ClassUtil;
import com.harbor.validate.validate.TrimUtils;
import com.harbor.validate.validate.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :Jooye
 * @date : 2024-03-23 23:16
 * @Describe: 类的描述信息
 */

@Aspect
@Slf4j
public class ValidateAop {

    {
        log.info("[参数校验]正在加载自定义校验模块...");
    }

    /**
     * 定义切面
     */
    @Pointcut(value = "@annotation(com.harbor.validate.Validate)")
    public void parameterValidate() {
    }


    @Before(value = "parameterValidate()")
    public void validate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("[参数校验]待校验参数为", args);
        if (args.length == 0) {
            log.error("[参数校验]待校验参数为空");
        }

        //基本类型
        this.basicType(joinPoint);

        //包装类型
        this.wrapperType(joinPoint);
    }

    private void wrapperType(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Parameter[] parameters = methodSignature.getMethod().getParameters();
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                if (ClassUtil.isBasicType(arg.getClass())) {
                    continue;
                }
                //执行验证操作
                ValidateUtils.validate(arg);
                //执行字符串类型去空格操作
                TrimUtils.trim(arg, Boolean.TRUE);

            }

        }
    }

    private void basicType(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Parameter[] parameters = method.getParameters();
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                if (!ClassUtil.isBasicType(arg.getClass())) {
                    continue;
                }
                Parameter parameter = parameters[i];
                List<Annotation> annotations = Arrays.stream(parameter.getAnnotations()).collect(Collectors.toList());
                ValidateUtils.validate(arg, annotations);
            }
        }
    }
}
