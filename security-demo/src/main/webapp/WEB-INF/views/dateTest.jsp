<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>DateTimeTest</title>
</head>
<body>

now : ${format.format(now)} <br />

<fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="short" />

</body>
</html>
