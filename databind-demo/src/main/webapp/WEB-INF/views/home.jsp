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
    //var personValObj = JSON.parse(personVal);
    // console.log(personVal);

    var personVal2 = '${person.jsonValue2}';
    var personValObj2 = JSON.parse(personVal2);
    console.log(personVal2);
    console.log(personValObj2);

    var personVal3 = '${person.jsonValue3}';
    /*var parseValueObj3 = JSON.parse(personVal3);
    console.log(parseValueObj3);*/
  });
</script>

</body>
</html>
