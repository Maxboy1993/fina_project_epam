<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome</h1>
<hr>
${user.firstName}, hello!"
</hr>
<form name="login" method="POST" action="controller"/>
<input type="hidden" name="command" value="logout">
<br/>
<input type="submit" value="logout">
</form>

<table>
    <c:forEach var="film" items="${filmsList}" varStatus="Status">
        <tr>
            <td><c:out value="${film.name}" /></td>
        </tr>
    </c:forEach>
</table>
<hr>
</body>
</html>
