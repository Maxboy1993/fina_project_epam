<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.signup_btn"/></title>
</head>
<body>
<hr>
<form name="signUp" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <br><fmt:message bundle="${pc}" key="label.firstName"/>:<br/>
    <input required type="text" name="firstName" placeholder="<fmt:message bundle="${pc}" key="label.firstName"/>"
           required pattern="[a-zA-Z]{2,50}" title="First Name format is incorrect"/>
    <br><fmt:message bundle="${pc}" key="label.lastNmae"/>:<br/>
    <input required type="text" name="lastName" placeholder="<fmt:message bundle="${pc}" key="label.lastNmae"/>"
           required pattern="[a-zA-Z]{2,50}" title="Last Name format is incorrect"/>
    <br><fmt:message bundle="${pc}" key="label.login"/>:<br/>
    <input required type="text" name="login" placeholder="<fmt:message bundle="${pc}" key="label.login"/>"
    required pattern="[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}\.[a-z]{2,4}" title="Login format is incorrect"/>

    <br><fmt:message bundle="${pc}" key="label.password"/>:<br/>
    <input required type="password" name="password" placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
           required pattern="[a-zA-Z0-9_.-@]{6,50}" title="Password format is incorrect"/>
    <br><fmt:message bundle="${pc}" key="label.birthday"/>:<br/>
    <input required type="date" name="birthday" placeholder="<fmt:message bundle="${pc}" key="label.birthday"/>"/>
    ${errorRegistrationPassMessage}
    <br/>
    <input type="submit" value="<fmt:message bundle="${pc}" key="label.signup_btn"/>"/>
</form>
<hr>
</body>
</html>
