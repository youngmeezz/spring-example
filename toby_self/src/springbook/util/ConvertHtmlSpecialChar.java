package springbook.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * HTML 특수 문자 변경 클래스
 * 
 * @author	: zaccoding
 * @Date	: 2017. 4. 13.		 
 *
 */
public class ConvertHtmlSpecialChar {
	/**
	 * html 특수문자 변환 처리
	 * 
	 * @param text 
	 * @return 특수문자 처리 된 문자열
	 */
	public static String convertHTML(String text) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
