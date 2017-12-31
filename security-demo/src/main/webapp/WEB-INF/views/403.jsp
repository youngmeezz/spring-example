<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017-12-31
  Time: 오후 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403 ERROR</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <h4>Access Denied!</h4>
    <a href="#" id="prevPage">move to prev page</a>
    <script>
        $(function(){
            $('#prevPage').on('click', function(e){
               e.preventDefault();
                window.history.go(-1);
            });
        });
    </script>
</body>
</html>
