<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<style>
#chatArea{
	width:200px; height:100px; over-flow-y:auto; border:1px solid black;
}
</style>
<body>

	이름 : <input type="text" id="nickname">
	<input type="button" id="enterBtn" value="입장">
	<input type="button" id="exitBtn" value="나가기">
	
	<h1>대화영역</h1>
	<div id="chatArea">
		<div id="chatMessageArea"></div>
	</div>
	<br />
	<input type="text" id="message">
	<input type="button" id="sendBtn" value="전송">
	
<script>
	var wsocket;
	function connect() {
		wsocket = new WebSocket("ws://localhost:8080/chat-ws");
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;
	};
	
	function disconnect() {
		wsocket.close();		
	};
	
	function onOpen(e) {
		appendMessage("연결되었습니다.");
	};
	
	function onMessage(e) {
		var data = e.data;
		if(data.substring(0,4) == "msg:") {
			appendMessage(data.substring(4));
		}
	}
	
	function onClose(e) {
		appendMessage("연결이 끊겼습니다.");
	};
	
	function send() {
		var nickname = $('#nickname').val();
		var msg = $('#message').val();
		wsocket.send("msg:" + nickname + ':' + msg);
		$('#message').val('');
	};
	
	function appendMessage(msg) {
		$('#chatMessageArea').append(msg + '<br>');
		var chatAreaHeight = $('#chatArea').height();
		var maxScroll = $('#chatMessageArea').height() - chatAreaHeight;
		$('#chatArea').scrollTop(maxScroll);
	};
	
	$(document).ready(function() {
		$('#message').keypress(function(e) {
			var keycode = (e.keyCode ? e.keyCode : e.which);
			if(keycode=='13') {
				send();
			}
			e.stopPropagation();
		});
		
		$('#sendBtn').on('click',function(){ send(); });
		$('#enterBtn').on('click',function(){ connect(); });
		$('#exitBtn').on('click',function(){ disconnect(); });
	});	

</script>

</body>
</html>