<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="data.pagecontent.locale" var="loc" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Test JSP</title>
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru"
                 var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en"
                 var="en_button" />
</head>
<body>
<form action="сontroller" method="post">
    <input type="hidden" name="local" value="ru" /> <input type="submit"
                                                           value="${ru_button}" /><br />
</form>
<form action="controller" method="post">
    <input type="hidden" name="local" value="en" /> <input type="submit"
                                                           value="${en_button}" /><br />
</form>
<c:out value="${message}" />
</body>
</html>