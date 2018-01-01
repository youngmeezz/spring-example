<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<form action="/loginPOST" method="post">
    <label for="loginid">ID3</label>:
        <input type="text" name="loginid" id="loginid" value="${loginid}"/>
    <br/>

    <label for="password">Password</label>:
    <input type="password" name="password" id="password"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <br/>
    <input type="submit" value="sign in" />
</form>

<p>
    ${failMessage}
</p>

</body>
</html>
