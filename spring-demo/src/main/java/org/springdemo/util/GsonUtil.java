package org.springdemo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
public class GsonUtil {
    private static Gson TO_STRING_GSON = new GsonBuilder().serializeNulls().create();

    public static String toString(Object inst) {
        if(inst == null) {
            return "null";
        }

        return TO_STRING_GSON.toJson(inst);
    }

    public static String printPretty(Object inst) {
        if(inst == null) {
            return "null";
        }

        return new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(inst);
    }



}
