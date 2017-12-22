<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<script>
	var result = '${savedName}';
	parent.addFilePath(result); //자신이 속한 화면의 바깥쪽 parent의 addFilePath() 함수를 호출
</script>
