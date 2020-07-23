<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.language"/></title>
</head>
<body>
<p>
    <fmt:message bundle="${pc}" key="label.language"/>
</p>

<ul>
    <li><a href="${pageContext.request.contextPath}/controller?command=CHANGE_LANGUAGE&language=en">
        <fmt:message bundle="${pc}" key="label.en"/></a></li>
    <li><a href="${pageContext.request.contextPath}/controller?command=CHANGE_LANGUAGE&language=ru">
        <fmt:message bundle="${pc}" key="label.ru"/></a></li>
</ul>
</body>
</html>
