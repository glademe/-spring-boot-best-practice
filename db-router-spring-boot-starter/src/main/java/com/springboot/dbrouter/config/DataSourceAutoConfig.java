package com.springboot.dbrouter.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :Jooye
 * @date : 2024-03-24 18:43
 * @Describe: 类的描述信息
 */
@Configuration
@SuppressWarnings("all")
public class DataSourceAutoConfig implements EnvironmentAware {

    /**
     * 分库全局属性
     */
    private static final String TAG_GLOBAl = "global";

    /**
     * 连接池属性
     */
    private static final String TAG_POOL = "pool";


    /**
     * 数据源配置组
     */
    private Map<String, Map<String,Object>> dataSourceMap = new HashMap<>();


    /**
     * 默认数据源配置
     */
    private Map<String,Object> defaultDataSourceConfig;

    /**
     * 分库数量
     */
    private int dbCount;

    /**
     * 分表数量
     */
    private int tbCount;


    /**
     * 路由字段
     */
    private String routerKey;


    @Override
    public void setEnvironment(Environment environment) {

    }
}
