<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome</h1>
<hr>
${user.firstName}, hello!"
</hr>

<form name="logOut" method="GET" action="controller"/>
<input type="hidden" name="command" value="log_out">
<br/>
<input type="submit" value="<fmt:message bundle="${pc}" key="label.logOut_btn"/>">
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
