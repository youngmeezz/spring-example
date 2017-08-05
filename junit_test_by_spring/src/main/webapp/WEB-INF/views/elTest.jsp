<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>EL TEST</title>
</head>
<body>

<c:if test="${elDomain.joined}">
	elDomain.joined
</c:if>

<c:if test="${!elDomain.joined}">
	not elDomain.joined
</c:if>



<form action="/el-test" method="post">
	<input type="checkbox" name="joined">
	<input type="submit" value="post">	
</form>



</body>
</html>