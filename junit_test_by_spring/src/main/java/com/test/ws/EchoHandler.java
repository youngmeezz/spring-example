package com.test.ws;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.test.domain.Admin;

public class EchoHandler extends TextWebSocketHandler/* Text 데이터 주고 받을 때 */ {
	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	
	/*
	  웹소켓 클라이언트가 연결되면 호출
	 */	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("## connected : " + session.getId());
	}
	
	/*
	  웹소켓 클라이언트가 데이터를 전송하면 호출. message는 클라이언트가 전송한 데이터를 담고 있음.
	 */
/*	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		this.handleTextMessage(session, (TextMessage)message);
	}	*/
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("receive");
		// receive
		logger.info("## [receive from client] session id : {} , payload{}", new Object[]{session.getId(), message.getPayload()} );
		Integer[] ids = new Gson().fromJson(message.getPayload(), new Integer[]{}.getClass());
		logger.info("## [ids] : {}",Arrays.toString(ids));
		
		// send
		//session.sendMessage(new TextMessage("echo : " + message.getPayload()));
		// test json
		Admin admin = new Admin();
		admin.setId("adminId");
		admin.setPassword("adminPassword");		
		session.sendMessage(new TextMessage(new Gson().toJson(admin)));
	}
	
	/*
	 웹소켓 클라이언트와의 연결에 문제가 발생하면 호출
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		
	}
	
	/*
	  웹소켓 클라이언트가 연결을 직접 끈헉나 서버에서 타임아웃이 발생해서 연결을 끊을 때 호출 된다.
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("## connection closed : " + session.getId());
	}

	/**
	  큰 데이터를 나눠서 받을 수 있는지 여부를 지정. 이 값이 true고 웹소켓 컨테이너(톰캣,jetty 등) 가 부분 메시지를 지원할 경우,
	 데이터가 크거나 미리 데이터의 크기를 알 수 없을때 handleMssage()를 여러 번 호출해서 데이터를 부분적으로 전달
	 */
	@Override
	public boolean supportsPartialMessages() {
		return super.supportsPartialMessages();
	}
	
	
	private void explain(WebSocketSession session) throws IOException {
		// 세션 ID 리턴
		session.getId();
		// 엔드포인트 리턴
		session.getUri();
		// 로컬 서버 주소 리턴
		session.getLocalAddress();
		// 클라이언트 주소를 리턴
		session.getRemoteAddress();
		// 소켓이 연결 있는지 여부 리턴
		session.isOpen();
		// 메시지를 전송
		session.sendMessage(null);
		// 소켓 종료
		session.close();
	}

}
