<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>GithubLookUp</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <c:set var="context" value="${pageContext.request.contextPath}"/>
</head>
<body>

<div id="message"></div>
<input type="text" name="user" id="user">
<input type="button" id="sendBtn" value="lookup">
<iframe id="append-frame" style="display: none;"></iframe>

<script>
  var resultMessageDiv = $('#message');
  var result = {
    append: function (messageObj) {
      console.log('append message', messageObj);
      resultMessageDiv.append("<div>" + messageObj.message + "</div>");
    }
  };
  $(function () {
    var userObj = $('#user');
    $('#sendBtn').on('click', function () {
      var user = userObj.val();
      console.log('click send btn ' + user);
      if (!user) {
        return;
      }

      var url = '${context}/async/github-lookup/' + user;
      document.getElementById('append-frame').src = url;
      //zacscoding
      /*$.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
          console.log('success to send github user lookup');
        }
      });*/
    });
  });
</script>
</body>
</html>
