package com.test.util;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import com.test.constant.Order;

public class StringUtils {
	
	/**
	 * 인트형 + 구분자 문자열을 정렬 순서에 맞게 다시 만드는 메소드
	 * e.g)
	 * 	value == "1,5,3" ==> Order == Sort.ASC ==> return ="1,3,5"
	 * 	value == "3,5,1" ==> Order == Sort.DESC ==> return = "5,3,1"
	 * 
	 * @author zaccoding
	 * @date 2017. 7. 23.
	 * @param value 
	 * 			구분할 문자열
	 * @param delim
	 * 			구분자
	 * @param order
	 * 			정렬
	 * @return
	 * 			정렬 된 int값 + ',' 구분자 문자열
	 */
	public static String getSortedStringWithDelimeter(final String value, final String delim, final Order order) {
		StringTokenizer st = new StringTokenizer(value, delim, false);
		// check size
		int tokenCnt = st.countTokens();
		if (tokenCnt < 1)
			return null;
		
		// offer priority queue 
		Comparator<String> comparator = new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				try {
					if(order == Order.ASC) 
						return Integer.parseInt(o1) - Integer.parseInt(o2);
					else 
						return Integer.parseInt(o2) - Integer.parseInt(o1);
				}
				catch(NumberFormatException e) {
					throw new IllegalArgumentException(e);
				}
			}
		};
		PriorityQueue<String> que = new PriorityQueue<>(comparator);
		while(st.hasMoreTokens()) {
			que.offer(st.nextToken());
		}
		
		// poll and create string
		StringBuilder sb = new StringBuilder(value.length());
		while (!que.isEmpty()) {
			sb.append(String.valueOf(que.poll()));
			if (!que.isEmpty())
				sb.append(",");
		}

		return sb.toString();
	}

}
