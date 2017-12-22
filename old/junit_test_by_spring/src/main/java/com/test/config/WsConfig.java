package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.test.ws.ChatWebSocketHandler;
import com.test.ws.EchoHandler;

@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(echoHandler(), "/echo.socketjs").withSockJS();
		registry.addHandler(chatHandler(), "/chat.socketjs").withSockJS();		
	}
	
	@Bean
	public EchoHandler echoHandler() {
		return new EchoHandler();
	}
	
	@Bean
	public ChatWebSocketHandler chatHandler() {
		return new ChatWebSocketHandler();
	}

}




