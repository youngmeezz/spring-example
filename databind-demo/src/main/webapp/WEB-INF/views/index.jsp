<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Data Bind Test</title>
</head>
<body>
<c:if test="${not empty message}">
    <h4>${message}</h4>
</c:if>
<form action="/data-bind/person" method="POST">
    <table style="width: 80%;">
        <%-- person.name --%>
        <tr>
            <td>
                <label for="name">Name</label>
                <input name="name" id="name"></td>
            </td>
        </tr>
        <%-- person.age --%>
        <tr>
            <td>
                <label for="age">Age</label>
                <input name="age" id="age"></td>
            </td>
        </tr>
        <%-- person.work  --%>
        <tr>
            <td>
                <label for="work">Work</label>
                <input type="checkbox" name="work" id="work" value="true">
            </td>
        </tr>
        <%-- person.intArray  --%>
        <tr>
            <td>
                <label>intArray</label> <br/>
                <input type="text" name="intArray" value="1"> <br/>
                <input type="text" name="intArray" value="2"> <br/>
            </td>
        </tr>
        <%-- person.intList  --%>
        <tr>
            <td>
                <label>intList</label> <br/>
                <input type="text" name="intList"> <br/>
                <input type="text" name="intList"> <br/>
                <input type="text" name="intList"> <br/>
            </td>
        </tr>
        <%-- person.address  --%>
        <tr>
            <td>
                <label>Address.addr</label> <input type="text" name="address.addr"> <br/>
                <label>Address.code</label> <input type="text" name="address.code"> <br/>
            </td>
        </tr>
        <%-- person.hobbies :: List<Hobby>  --%>
        <tr>
            <td>
                <label>hobby1</label>
                <input type="text" name="hobbies[0].hobby"> <input type="text" name="hobbies[0].priority"> <br />

                <%-- skip index :: 1 => will be null in hobbies--%>
                <label>hobby3</label>
                <input type="text" name="hobbies[2].hobby"> <input type="text" name="hobbies[2].priority"> <br />
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit">SUBMIT</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
