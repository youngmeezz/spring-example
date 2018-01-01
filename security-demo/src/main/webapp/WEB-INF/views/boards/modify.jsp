<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modify Board</title>
</head>
<body>
<form action="/boards/modify" method="POST">
    <input type="hidden" name="id" value="${board.id}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <table style="width:60%;">
        <tr>
            <td>title</td>
            <td>
                <input name="title" value="${board.writer}">
            </td>
        </tr>
        <tr>
            <td>writer</td>
            <td>
                <input name="writer" value="${board.writer}" readonly>
            </td>
        </tr>

        <tr>
            <td>content</td>
            <td>
                <textarea name="content" cols="20" rows="10">${board.content}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit" value="submit">submit</button>
                <button type="reset" value="reset">reset</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
