<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--<fmt: setLocale value = "${sessionScope.lang}"  scope="session"/>--%>
<fmt:setLocale value="ru" scope="session"/>
<fmt:setBundle basename="resource.pagecontent.pagecontent" var="pc"/>

<%--<html lang = "${param.lang}">--%>
<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.login"/></title>
    <fmt:message bundle="${pc}" key="label.language"/>
    <fmt:message bundle="${pc}" key="label.en"/>
    <fmt:message bundle="${pc}" key="label.ru"/>
    <fmt:message bundle="${pc}" key="label.confirm_btn"/>
    <fmt:message bundle="${pc}" key="label.login"/>
    <fmt:message bundle="${pc}" key="label.password"/>
    <fmt:message bundle="${pc}" key="label.login_btn"/>
    <fmt:message bundle="${pc}" key="label.signup_btn"/>
</head>
<body>

<fmt:message bundle="${pc}" key="label.language"/>
<fmt:message bundle="${pc}" key="label.en"/>
<fmt:message bundle="${pc}" key="label.ru"/>
<fmt:message bundle="${pc}" key="label.confirm_btn"/>
<fmt:message bundle="${pc}" key="label.login"/>
<fmt:message bundle="${pc}" key="label.password"/>
<fmt:message bundle="${pc}" key="label.login_btn"/>
<fmt:message bundle="${pc}" key="label.signup_btn"/>

</body>
</html>
