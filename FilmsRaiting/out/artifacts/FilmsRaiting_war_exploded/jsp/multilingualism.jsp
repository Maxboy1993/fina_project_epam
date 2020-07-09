<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="resource.pagecontent.pagecontent" var="pc"/>

<html lang="${param.lang}">
<head>
    <title><fmt:message bundle="${pc}" key="label.language"/></title>
</head>
<body>
<h2>
    <fmt:message bundle="${pc}" key="label.language"/>
</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/jsp/login.jsp?lang=en"><fmt:message bundle="${pc}" key="label.en"/></a></li>
    <li><a href="${pageContext.request.contextPath}/jsp/login.jsp?lang=ru"><fmt:message bundle="${pc}" key="label.ru"/></a></li>
</ul>
</body>
</html>
