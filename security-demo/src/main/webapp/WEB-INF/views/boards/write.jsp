<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write board</title>
</head>
<body>
    <form action="/boards/write" method="POST">
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
                    <input name="writer">
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
