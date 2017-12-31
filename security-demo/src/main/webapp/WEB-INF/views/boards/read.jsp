<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017-12-31
  Time: 오후 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time"%>

<html>
<head>
    <title>View Board</title>
</head>
<body>
    <table style="width:60%;">
        <tr>
            <td>No</td>
            <td>${board.id}</td>
        </tr>

        <tr>
            <td></td>title</td>
            <td>${board.title}</td>
        </tr>

        <tr>
            <td>content</td>
            <td>
                <textarea readonly>${board.content}</textarea>
            </td>
        </tr>

        <tr>
            <th>update date</th>
            <td>
                <javatime:parseLocalDateTime value="${board.regDate}" pattern="yyyy-MM-ddTHH:mm:ss" var="parsedDate"/>
            </td>
        </tr>
        <tr>
            <th>modify date</th>
            <td>
                <c:choose>
                    <c:when test="${empty board.modDate}">
                        -
                    </c:when>
                    <c:otherwise>
                        <javatime:parseLocalDateTime value="${board.modDate}" pattern="yyyy-MM-ddTHH:mm:ss" var="parsedDate"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
    <a href="/boards/modify/${board.id}">Modify</a>
    <a href="/boards/delete/${board.id}">Delete</a>
</body>
</html>
