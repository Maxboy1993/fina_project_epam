<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Login</title>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resourses/data/pagecontent/pagecontent" var="loc"/>
    <fmt:message bundle="${loc}" key="local.message.choose_language" var="choose_language_mes"/>
    <fmt:message bundle="${loc}" key="local.message.langauage.en" var="langauage_en_mes"/>
    <fmt:message bundle="${loc}" key="local.message.langauage.ru" var="langauage_ru_mes"/>
    <fmt:message bundle="${loc}" key="local.button.confirm" var="confirm_btn"/>
    <fmt:message bundle="${loc}" key="local.message.login" var="login_mes"/>
    <fmt:message bundle="${loc}" key="local.message.password" var="password_mes"/>
    <fmt:message bundle="${loc}" key="local.button.login" var="login_btn"/>
    <fmt:message bundle="${loc}" key="local.button.sign_up" var="sign_up_btn"/>
</head>
<body>

<%-- EN--%>
<form name="languageForm" method="POST" action="controller">
    <input type="hidden" name="local" value="en"/>
    <c:out value="${choose_languahe_mes}"/>
    <br>
    <input type="radio" name="language" value="english"/>
    <c:out value="${langauage_en_mes}"/>
    <input type="radio" name="language" value="russian"/>
    <c:out value="${langauage_ru_mes}"/>
    <br/>
    <input type="submit" value="${confirm_btn}"/>
</form>
<hr>

<%-- EN--%>
<form name="languageForm" method="POST" action="controller">
    <input type="hidden" name="local" value="ru"/>
    <c:out value="${choose_language_mes}"/>
    <br>
    <input type="radio" name="language" value="english"/>
    <c:out value="${langauage_en_mes}"/>
    <input type="radio" name="language" value="russian"/>
    <c:out value="${langauage_ru_mes}"/>
    <br/>
    <input type="submit" value="${confirm_btn}"/>
</form>
<hr>

<%-- EN--%>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login"/>
    <input type="hidden" name="local" value="en"/>
    <c:out value="${login_mes}"/>
    <input required type="text" name="login" placeholder="${login_mes}"/>
    <c:out value="${password_mes}"/>
    <input required type="password" name="password" placeholder="${password_mes}"/>
    ${errorLoginPassMessage}
    <br/>
    <input type="submit" value="${login_btn}"/>
</form>
<hr>

<%-- RU--%>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login"/>
    <input type="hidden" name="local" value="ru"/>
    <c:out value="${login_mes}"/>
    <input required type="text" name="login" placeholder="${login_mes}"/>
    <c:out value="${password_mes}"/>
    <input required type="password" name="password" placeholder="${password_mes}"/>
    ${errorLoginPassMessage}
    <br/>
    <input type="submit" value="${login_btn}"/>
</form>
<hr>

<%-- EN--%>
<form name="login" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <input type="hidden" name="local" value="en"/>
    <input type="submit" value="${sign_up_btn}"/>
</form>
<hr>

<%-- RU--%>
<form name="login" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <input type="hidden" name="local" value="ru"/>
    <input type="submit" value="${sign_up_btn}"/>
</form>
<hr>
</body>
</html>
