<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

${title}

<script>
  $(function () {
    var personVal = '${person.jsonValue}';
    var personVal2 = '${person.jsonValue2}';
    //var personValObj = JSON.parse(personVal);
    var personValObj2 = JSON.parse(personVal2);
    console.log(personVal);
    console.log(personVal2);
    console.log(personValObj2);
  });
</script>

</body>
</html>
