<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<fmt:setLocale value="${param.lang}" scope="session"/>
<fmt:setBundle basename="resource.pagecontent.pagecontent" var="pc"/>

<html lang="${param.lang}">
<head>
    <title><fmt:message bundle="${pc}" key="label.login"/></title>
    <%--    <fmt:message bundle="${pc}" key="label.language"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.en"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.ru"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.confirm_btn"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.login"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.password"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.login_btn"/>--%>
    <%--    <fmt:message bundle="${pc}" key="label.signup_btn"/>--%>
</head>
<body>
<c:import url="multilingualism.jsp"/>
<hr>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_in"/>
    <fmt:message bundle="${pc}" key="label.login"/>
    <br>
    <input required type="text" name="login" placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
            required pattern="[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}\.[a-z]{2,4}" title="Login format is incorrect"/>
    <br>
    <fmt:message bundle="${pc}" key="label.password"/>
    <br>
    <input required type="password" name="password" placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
           required pattern="[a-zA-Z0-9_.-@]{8,50}" title="Password format is incorrect"/>
    <br>
    ${errorLoginPassMessage}
    <br>
    <input type="submit" value=<fmt:message bundle="${pc}" key="label.signIn_btn"/>>
</form>
<hr>

<form name="signup" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <input type="submit" value="<fmt:message bundle="${pc}" key="label.signup_btn"/>">
</form>
<hr>
</body>
</html>
