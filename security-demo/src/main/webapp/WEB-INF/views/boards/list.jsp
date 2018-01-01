<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Board List</title>
</head>
<body>
    <table style="width:60%">
        <tr>
            <td>id</td> <td>title</td> <td>writer</td> <td>reg date</td> <td>mod date</td> <td>modify</td> <td>delete</td>
        </tr>
        <c:if test="${empty boards}">
            <tr>
                <td colspan="100%">Not Exist Boards</td>
            </tr>
        </c:if>
        <c:forEach var="board" items="${boards}">
            <tr>
                <td>${board.id}</td>
                <td>
                    <a href="/boards/read/${board.id}">${board.title}</a>
                </td>
                <td>${board.writer}</td>
                <td>${board.regDate}</td>
                <td>${board.modDate}</td>
                <td><a href="/boards/modify/${board.id}">modify</a></td>
                <td><a href="/boards/delete/${board.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/boards/write">Regist</a> <br />
    <sec:authorize access="isAuthenticated()">
        <form action="/logout" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button>LOGOUT</button>
        </form>
    </sec:authorize>



</body>
</html>