package org.springdemo.util;

/**
 * @author zacconding
 * @Date 2018-02-24
 * @GitHub : https://github.com/zacscoding
 */
public class ThreadUtil {
    public static String getThreadInfo() {
        Thread thread = Thread.currentThread();

        if(thread == null) {
            return "Thread : null";
        }

        return "Thread id : " + thread.getId() + ", name : " + thread.getName();
    }


}
