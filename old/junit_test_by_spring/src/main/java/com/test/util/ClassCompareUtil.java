package com.test.util;

/**
 * Compare class for getters
 * 
 * @author zaccoding
 * @date 2017. 8. 5.
 */
public class ClassCompareUtil {
	/**
	 * Compare to class
	 * 
	 * @author zaccoding
	 * @date 2017. 8. 5.
	 * @param o1 Object1 to compare
	 * @param o2 Object1 to compare
	 * @param clazz compare class type
	 * @return true (if equals for getters) or false  
	 */
	public<T> boolean isEquals(T o1, T o2, Class<T> clazz) {
		// both null
		if(o1 == null && o2 == null)
			return true;
		// only one null
		if(o1 == null || o2 == null)
			return false;		
		return false;
	}
	

}
