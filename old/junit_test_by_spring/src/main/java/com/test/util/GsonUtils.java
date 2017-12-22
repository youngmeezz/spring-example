package com.test.util;

import com.google.gson.GsonBuilder;

public class GsonUtils {	
	public static String toStringFromGson(Object obj) {
		return new GsonBuilder().serializeNulls().create().toJson(obj);
	}

}
