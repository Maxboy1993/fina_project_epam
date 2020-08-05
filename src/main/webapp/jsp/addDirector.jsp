<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.addDirector"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="cancelAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_add_Actor">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.cancel"/></button>
        </form>
    </div>
    <div class="form-inline">
        <form class="form-inline" name="continueAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_add_film">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.addFilm"/></button>
        </form>
    </div>
</nav>
<br>
<c:if test="${not empty directorAddingError}">
    <fmt:message bundle="${pc}" key="${directorAddingError}"/>
</c:if>
<br>
<c:if test="${not empty listDirectorAddingError}">
    <c:forEach items="${listDirectorAddingError}" var="directorError">
        <fmt:message bundle="${pc}" key="${directorError}"/>
        <br>
    </c:forEach>
</c:if>
<div class="wrapper">
    <form class="form-signin" name="addDirector" action="controller" method="POST">
        <input type="hidden" name="command" value="add_director">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.inputDirectorData"/></h5>
        <input type="text" class="form-control" name="firstName"
               placeholder="<fmt:message bundle="${pc}" key="label.firstName"/>"
               required pattern="[a-zA-Z\s]{2,30}" title="First Name format is incorrect"/>
        <input type="text" class="form-control" name="lastName"
               placeholder="<fmt:message bundle="${pc}" key="label.lastNmae"/>"
               required pattern="[a-zA-Z\s]{2,30}" title="Last Name format is incorrect"/>
        <input type="date" class="form-control" name="birthday"
               placeholder="<fmt:message bundle="${pc}" key="label.birthday"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.addDirector"/></button>
    </form>
</div>
</body>
</html>

