<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<h1>
    <div style="width:80%;">
        <div id="messageDiv"></div>
        <input type="text" id="message"/>
        <input type="button" id="btnSend" value="Send"/>
    </div>
</h1>

<script src="/resources/js/chat.js" type="text/javascript"></script>

<script>
  $(function () {
    var messageObj = $('#message');
    var messageDiv = $('#messageDiv');
    var hasError = false;

    var getMessage = function () {
      console.log('getMessage is started..');
      if (hasError) {
        return;
      }

      $.ajax({
        url: "/chat/messages",
        type: 'GET',
        headers: {
          "Content-Type": "application/json"
        },
        success: function (result) {
          messageDiv.append(result + '<br />');
          console.log("success to get message", result);
        },
        error: function (xhr) {
          alert('error');
          console.log(xhr);
          hasError = true;
        },
        complete: getMessage
      });
    };

    (function init() {
      getMessage();
    })();

    $(document).on('click', '#btnSend', function () {
      var message = messageObj.val();
      if (!message) {
        return;
      }

      $.ajax({
        url: "/chat/messages",
        type: 'POST',
        headers: {
          "Content-Type": "application/json",
          "X-HTTP-Method-Override": "POST",
        },
        data: JSON.stringify(message),
        success: function () {
          console.log('success to send');
          messageObj.val('');
        },
        error: function (xhr) {
          alert('error');
          console.log(xhr);
        }
      });

    });
  });
</script>

</body>
</html>