<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<fmt:setLocale value="${pageContext.session.getAttribute('language')}"/>
<fmt:setBundle basename="resource.pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.language"/></title>
</head>
<body>
<h2>
    <fmt:message bundle="${pc}" key="label.language"/>
</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/jsp/signIn.jsp?language=en"><fmt:message bundle="${pc}"
                                                                                             key="label.en"/></a></li>
    <li><a href="${pageContext.request.contextPath}/jsp/signIn.jsp?language=ru"><fmt:message bundle="${pc}"
                                                                                             key="label.ru"/></a></li>
</ul>
</body>
</html>
