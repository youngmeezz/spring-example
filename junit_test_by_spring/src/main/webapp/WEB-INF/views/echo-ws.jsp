<!-- https://www.w3.org/TR/websockets/ -->
<!-- https://github.com/sockjs/sockjs-client -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Echo</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>	
	<script>
		$(document).ready(function() {
			$('#btnSend').on('click',function(){
				sendMessage();
			});						
		});
		
		var wsocket;
		function sendMessage() {
			// 일반 웹소켓
			//wsocket = new WebSocket("ws://localhost:8080/echo-ws");
			// SocketJS
			wsocket = new SockJS("http://localhost:18080/echo.socketjs");
			wsocket.onmessage = onMessage;
			wsocket.onclose = onClose;
			wsocket.onopen = function() { //서버에 연결되면 실행
				wsocket.send($('#message').val()); // 서버에 메시지 전송
			};
		};
		
		function onMessage(e) {
			/* TEST TEXT MESSAGE*/
			/* var data = e.data;
			alert('받음 : ' + data); */
			
			
			/* TEST TEXT MESSAGE -> JSON */
			//var admin = eval('(' + e.data + ')'); // success			
			var admin = JSON.parse(e.data);
			var id = admin.id;
			var password = admin.password;
			alert('id = ' + id + ', pw : ' + password);
			wsocket.close();
		};
		
		function onClose(e) {
			alert('연결 끊김');
		}	
		
	</script>
</head>
<body>

	<input type="text" id="message">
	<input type="button" id="btnSend" value="send">
	
</body>
</html>