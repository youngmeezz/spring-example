package com.test.ws;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 간단 채팅
 * @author zaccoding
 * @date 2017. 7. 16.
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
	private Map<String,WebSocketSession> users = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("conneted");
		logger.info("## connected : " + session.getId());
		users.put(session.getId(), session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("## closed : " + session.getId());
		users.remove(session.getId());
	}
	
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("## [" + session.getId() + "] : " + message.getPayload());
		users.forEach((k,v)-> {
			try {
				v.sendMessage(message);
				logger.info("## [send] > " + v.getId() + "[ " + message.getPayload() + "]");
			} catch (IOException e) {
				e.printStackTrace();				
			}
		});
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info("## [ERROR] : "+session.getId() + exception.getMessage());
	}
}












