package com.demo.thread;

/**
 * Current thread`s ThreadLocalContext manager
 * if u use multiple context, define contexts such as "private static Map<Class<?>, ThreadLocalContext>"
 * @author zacconding
 * @Date 2018-01-03
 * @GitHub : https://github.com/zacscoding
 */
public class ThreadLocalManager {
    private static ThreadLocal<ThreadLocalContext> contexts = new ThreadLocal<>();

    public static ThreadLocalContext getContext() {
        return contexts.get();
    }

    public static ThreadLocalContext getContextAndCreateIfNotExist() {
        if (contexts.get() == null) {
            contexts.set(new ThreadLocalContext());
        }

        return contexts.get();
    }

    public static ThreadLocalContext clearContext() {
        ThreadLocalContext context = contexts.get();
        if(context != null) {
            contexts.remove();
        }
        return context;
    }
}
