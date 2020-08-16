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
    <title><fmt:message bundle="${pc}" key="label.signIn_btn"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="dropdown ">
        <button class="btn btn-outline-success my-2 my-sm-0 dropdown-toggle" type="button" id="dropdownMenuButton"
                data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <fmt:message bundle="${pc}" key="label.language"/>
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/controller?command=CHANGE_LANGUAGE&language=en">
                <fmt:message bundle="${pc}" key="label.en"/></a>
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/controller?command=CHANGE_LANGUAGE&language=ru">
                <fmt:message bundle="${pc}" key="label.ru"/></a>
        </div>
    </div>
    <form class="form-inline" name="signUp" method="GET" action="controller">
        <input type="hidden" name="command" value="passing_to_sign_up"/>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.signup_btn"/></button>
    </form>
</nav>

<br>
<c:if test="${ not empty wrongCommandMessage}">
    <fmt:message bundle="${pc}" key="${wrongCommandMessage}"/>
</c:if><br>
<c:if test="${ not empty noRightForCommandMessage}">
    <fmt:message bundle="${pc}" key="${noRightForCommandMessage}"/>
</c:if>
<br>
<c:if test="${ not empty verification}">
    <fmt:message bundle="${pc}" key="${verification}"/>
</c:if>
<br>
<c:if test="${ not empty errorLoginOrPasswordSearchingMessage}">
    <fmt:message bundle="${pc}" key="${errorLoginOrPasswordSearchingMessage}"/>
</c:if>
<br>
<c:if test="${not empty errorUserValidationMessage}">
    <c:forEach var="errorUserValidation" items="${errorUserValidationMessage}">
        <fmt:message bundle="${pc}" key="${errorUserValidation}"/>
        <br>
    </c:forEach>
</c:if>

<div class="wrapper">
    <form class="form-signin" name="signInForm" method="POST" action="controller">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.signIn_btn"/></h5>
        <input type="hidden" name="command" value="sign_in"/>
        <input type="text" class="form-control" name="login"
               placeholder="<fmt:message bundle="${pc}" key="label.login"/>(e-mail):" autofocus="" required
               pattern="[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}\.[a-z]{2,4}"
               title="Login format is incorrect"/>
        <input type="password" class="form-control" name="password"
               placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
               required pattern="[a-zA-Z0-9_.-@]{6,50}" title="Password format is incorrect"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.signIn_btn"/></button>
    </form>
</div>

<br>
<c:if test="${ not empty errorFilmSearching}">
    <fmt:message bundle="${pc}" key="${errorFilmSearching}"/>
</c:if>

<ul class="list-unstyled">
    <c:forEach var="film" items="${filmsList}" varStatus="Status">
        <li class="media">
            <img src="data:image/jpg;base64,${film.poster}" class="mr-3" alt="POSTER" width="100" height="150">
            <div class="media-body">
                <h5 class="mt-0 mb-1"> ${film.name}</h5>
                <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.releaseDate"/>: <span
                        class="badge badge-secondary">${film.releaseDate}</span>
                </h8>
                <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.filmRaiting"/><span
                        class="badge badge-secondary"> ${film.raiting}</span></h8>
            </div>
        </li>
        <br>
    </c:forEach>
</ul>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
