<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <!-- security csrf -->
    <!--
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    -->
    <title>Write board</title>
</head>
<body>
    <sec:authentication property="principal.member" var="member"/>
    <form action="/boards/write" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <table style="width:60%;">
            <tr>
                <td>title</td>
                <td>
                    <input name="title">
                </td>
            </tr>
            <tr>
                <td>writer</td>
                <td>
                    <input name="writer" value="${member.loginId}" readonly>
                </td>
            </tr>
            <tr>
                <td>content</td>
                <td>
                    <textarea name="content" cols="20" rows="10"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit">Submit</button>
                    <button type="reset">Reset</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
