<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring MVC Excel View</title>
</head>
<body>
	<table>
	    <tr>
	        <th>ID</th>
	        <th>Name</th>
	        <th>Date</th>
	    </tr>
	    <c:forEach var="c" items="${customers}">
	        <tr>
	            <td>${c.seq}</td>
	            <td>${c.name}</td>
	            <td>${c.cellphone}</td>
	            <td>${c.email}</td>	            
	        </tr>
	    </c:forEach>
	</table>
</body>
</html>