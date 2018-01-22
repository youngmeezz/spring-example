package com.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 * @Date : 2018-01-22
 */
public class GsonUtil {
    private static final GsonBuilder builder = new GsonBuilder().serializeNulls();
    public static String prettyPrint(Object inst) {
        new GsonBuilder().serializeNulls();
        return builder.setPrettyPrinting().create().toJson(inst);
    }

    public static String toJson(Object inst) {
        return builder.create().toJson(inst);
    }


}
