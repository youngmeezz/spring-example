<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>차단</title>
<script>
	function check() {
		alert(navigator.userAgent);
	}
	function is_ie() {
	  if(navigator.userAgent.toLowerCase().indexOf("chrome") != -1) {
		  console.log('include chrome');
		  return false;
	  }
	  if(navigator.userAgent.toLowerCase().indexOf("msie") != -1) {
		  console.log('include msie');
		  return true;
	  }
	  if(navigator.userAgent.toLowerCase().indexOf("trident") != -1) {
		  console.log('include trident');
		  return true;
	  }
	  if(navigator.userAgent.toLowerCase().indexOf("windows nt") != -1){
		  console.log('include windows');
		  return true;
	  }	  
	  return false;
	}	
	
	function copy_to_clipboard(str) {	  	
	  if( is_ie() ) {
	    window.clipboardData.setData("Text", str);
		goBack(-2);
	  }
	  prompt("Ctrl+C를 눌러 복사하세요.", str);
	  goBack(-1);	  
	}
	
	function goBack(backCount) {
		history.go(backCount);
	}
	
	function checkHistory() {
		alert(window.history.length);
	}	
	
	function openNewWindow() {
		var url = "http://localhost:18080/notice";
		var specs = "left=10,top=10,width=372,height=466";
		specs += ",toolbar=no,menubar=no,status=no,scrollbars=no,resizable=no";
		window.open(url,'', specs);
	}
</script>	
</head>
<body>

<button onclick="copy_to_clipboard('Hello Jmnote2');">복사</button>
<button onclick="check();">Check browser</button>
<button onclick="checkHistory();">Check History</button>
<button onclick="openNewWindow();">open</button>

</body>
</html>