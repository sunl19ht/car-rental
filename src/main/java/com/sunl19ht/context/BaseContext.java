package com.sunl19ht.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Integer getCurrentId() {
        return Math.toIntExact(threadLocal.get());
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
