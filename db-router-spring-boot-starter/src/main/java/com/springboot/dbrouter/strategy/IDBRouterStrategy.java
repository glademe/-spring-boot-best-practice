package com.springboot.dbrouter.strategy;

/**
 * @author :Jooye
 * @date : 2024-03-24 19:06
 * @Describe: 类的描述信息
 */
public interface IDBRouterStrategy {

    /**
     * 路由计算
     * @param dbKeyAttr 路由字段
     */
    void doRouter(String dbKeyAttr);

    /**
     * 手动设置分库路由
     * @param dbIdx
     */
    void setDBKey(int dbIdx);


    /**
     * 手动设置分表路由
     * @param tbIdx
     */
    void setTBKey(int tbIdx);


    /**
     * 获取分库数
     *
     * @return 数量
     */
    int dbCount();

    /**
     * 获取分表数
     *
     * @return 数量
     */
    int tbCount();

    /**
     * 清除路由
     */
    void clear();

}
