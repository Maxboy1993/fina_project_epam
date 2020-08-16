<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<fmt:setLocale value="en"/>
<c:if test="${ not empty language}">
    <fmt:setLocale value="${language}"/>
</c:if>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.addFilm"/></title>
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
            <input type="hidden" name="command" value="passing_to_add_Director">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.cancel"/></button>
        </form>
    </div>
</nav>

<br>
<c:if test="${not empty filmAddingError}">
    <fmt:message bundle="${pc}" key="${filmAddingError}"/>
</c:if>
<br>
<c:if test="${not empty listFilmAddingError}">
    <c:forEach var="filmError" items="${listFilmAddingError}">
        <fmt:message bundle="${pc}" key="${filmError}"/>
        <br>
    </c:forEach>
</c:if>

<div class="wrapper">
    <form class="form-signin" name="addFilmr" action="controller" method="POST">
        <input type="hidden" name="command" value="add_film">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.inputFilmData"/></h5>
        <input type="text" class="form-control" name="filmName"
               placeholder="<fmt:message bundle="${pc}" key="label.filmName"/>"
               required pattern="[a-zA-Z0-9\s]{1,100}" title="Film Name format is incorrect"/>
        <input type="text" class="form-control" name="genre"
               placeholder="<fmt:message bundle="${pc}" key="label.genre"/>"
               required pattern="[a-zA-Z\s]{5,50}" title="Genre Name format is incorrect"/>
        <input type="date" class="form-control" name="releaseDate"
               placeholder="<fmt:message bundle="${pc}" key="label.releaseDate"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.addPoster"/></button>
    </form>
</div>
</body>
</html>

