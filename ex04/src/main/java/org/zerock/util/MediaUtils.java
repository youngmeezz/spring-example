package org.zerock.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
public class MediaUtils {
	private static Map<String,MediaType> mediaMap;	
	static {
		mediaMap = new HashMap<String,MediaType>();
		mediaMap.put("JPG",MediaType.IMAGE_JPEG);
		mediaMap.put("GIF",MediaType.IMAGE_GIF);
		mediaMap.put("PNG",MediaType.IMAGE_PNG);	
	}
	
	// 브라우저에서 파일을 다운 OR 보여줄 것인지 결정하기 위해
	// 이미지 타입인지 판단.
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
}
