package com.springboot.dbrouter;

/**
 * @author :Jooye
 * @date : 2024-03-24 18:54
 * @Describe: 数据源基础配置
 */
public class DBRouterBase {
    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }

}
