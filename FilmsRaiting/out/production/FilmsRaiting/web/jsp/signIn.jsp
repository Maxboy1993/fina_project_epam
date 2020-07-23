<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.signIn_btn"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr>
<p>
    ${verification}
</p>
<form name="signInForm" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_in"/>
    <fmt:message bundle="${pc}" key="label.login"/>
    <br>
    <input required type="text" name="login" placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
           required pattern="[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}\.[a-z]{2,4}" title="Login format is incorrect"/>
    <br>
    <fmt:message bundle="${pc}" key="label.password"/>
    <br>
    <input required type="password" name="password" placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
           required pattern="[a-zA-Z0-9_.-@]{6,50}" title="Password format is incorrect"/>
    <br>
    ${errorLoginOrPasswordValidationMessage}
    ${errorLoginOrPasswordSearchingMessage}
    <br>
    <input type="submit" value=<fmt:message bundle="${pc}" key="label.signIn_btn"/>>
</form>
<hr>

<form name="sigUup" method="GET" action="controller">
    <input type="hidden" name="command" value="passing_to_sign_up"/>
    <input type="submit" value="<fmt:message bundle="${pc}" key="label.signup_btn"/>">
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
