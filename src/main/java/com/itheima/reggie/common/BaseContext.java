package com.itheima.reggie.common;

/**
 * 基于threadlocal封装工具类，保存当前线程的用户id和获取当前线程的用户id
 *
 * @author Wangmin
 * @date 2023/3/29 16:22
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void serCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();

    }
}
