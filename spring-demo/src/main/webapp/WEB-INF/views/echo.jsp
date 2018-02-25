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
<label>Repeat : </label><input type="text" id="repeat"> <br />
<label>Sleep : </label><input type="text" id="sleep"> <br />
<label>Message : </label><input type="text" id="sendMessage"> <br />
<input type="button" id="sendBtn" value="submit">
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
    var repeatObj = $('#repeat');
    var sleepObj = $('#sleep');
    var sendMessageObj = $('#sendMessage');
    $('#sendBtn').on('click', function () {
      resultMessageDiv.empty();

      var repeat = repeatObj.val();
      var sleep = sleepObj.val();
      var sendMessage = sendMessageObj.val();

      if(!repeat) {
        repeat = 1;
      }

      if(!sleep) {
        sleep = 500;
      }
      // /echo/{message}/{repeat}/{sleep}
      var url = '${context}/async/echo/' + sendMessage + '/' + repeat + '/' + sleep;
      document.getElementById('append-frame').src = url;
    });
  });
</script>
</body>
</html>
