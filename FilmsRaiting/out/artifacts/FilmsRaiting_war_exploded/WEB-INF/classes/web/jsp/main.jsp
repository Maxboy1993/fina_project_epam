<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="ctd" uri="customDate" %>

<fmt:setLocale value="en"/>
<c:if test="${ not empty language}">
    <fmt:setLocale value="${language}"/>
</c:if>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.welcome"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="findFilm" method="GET" action="controller"/>
        <input type="hidden" name="command" value="FIND_FILM_BY_NAME">
        <input class="form-control mr-sm-2" required type="text" name="filmName"
               placeholder="<fmt:message bundle="${pc}" key="label.filmName"/>"
               aria-label="Search" required pattern="[a-zA-Z0-9\s]{1,100}" title="Film Name format is incorrect"/>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.findFilm"/></button>
        </form>
    </div>
    <div class="form-inline">
        <form class="form-inline" name="logOut" method="GET" action="controller"/>
        <input type="hidden" name="command" value="log_out">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.logOut_btn"/></button>
        </form>
    </div>
</nav>
<c:if test="${userRole == 'ADMIN'}">
    <hr>
    <nav class="navbar navbar-light bg-light">
        <div class="form-inline">
            <form class="form-inline" name="addFilmData" method="GET" action="controller"/>
            <input type="hidden" name="command" value="passing_to_add_actor">
            <button class="btn btn-success" type="submit"><fmt:message bundle="${pc}"
                                                                       key="label.addFilm"/></button>
            </form>
        </div>
        <div class="form-inline">
            <form class="form-inline" name="findFilm" method="GET" action="controller"/>
            <input type="hidden" name="command" value="FIND_ALL_USERS">
            <button class="btn btn-success" type="submit"><fmt:message bundle="${pc}"
                                                                       key="label.showUsers"/></button>
            </form>
        </div>
    </nav>
</c:if>
<hr>
<form class="form-inline" name="showProfil" method="GET" action="controller"/>
<input type="hidden" name="command" value="PASSING_TO_USER_PROFILE">
<button class="btn btn-success" type="submit"><fmt:message bundle="${pc}"
                                                           key="label.userProfile"/></button>
</form>

<br>
<c:if test="${not empty verification}">
    <fmt:message bundle="${pc}" key="${verification}"/>
</c:if>
<br>
<c:if test="${not empty noFilmsFound}">
    <fmt:message bundle="${pc}" key="${noFilmsFound}"/>
</c:if>
<br>
<c:if test="${not empty errorFilmSearching}">
    <fmt:message bundle="${pc}" key="${errorFilmSearching}"/>
</c:if>

<c:if test="${not empty raitingCountingError}">
    <fmt:message bundle="${pc}" key="${raitingCountingError}"/>
</c:if>
<c:if test="${filmsListByName == null}">
    <ul class="list-unstyled">
        <c:forEach var="film" items="${filmsList}" varStatus="Status">
            <li class="media">
                <img src="data:image/jpg;base64,${film.poster}" class="mr-3" alt="POSTER" width="100" height="150">
                <div class="media-body">
                    <h5 class="mt-0 mb-1"><a
                            href="${pageContext.request.contextPath}/controller?command=SHOW_FILM_INFO&filmId=${film.id}">${film.name}</a>
                    </h5>
                    <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.releaseDate"/>: <span
                            class="badge badge-secondary"><ctd:custom-date date="${film.releaseDate}"/></span></h8>
                    <div class="dropdown ">
                        <button class="btn btn-outline-success my-2 my-sm-0 dropdown-toggle" type="button"
                                id="dropdownMenuButton"
                                data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <fmt:message bundle="${pc}" key="label.filmRaiting"/> ${film.raiting}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=1&filmId=${film.id}">
                                1</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=2&filmId=${film.id}">
                                2</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=3&filmId=${film.id}">
                                3</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=4&filmId=${film.id}">
                                4</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=5&filmId=${film.id}">
                                5</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=6&filmId=${film.id}">
                                6</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=7&filmId=${film.id}">
                                7</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=8&filmId=${film.id}">
                                8</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=9&filmId=${film.id}">
                                9</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=10&filmId=${film.id}">
                                10</a>
                        </div>
                    </div>
                    <c:if test="${userRole == 'ADMIN'}">
                    <p class="mt-0 mb-1">
                    <form class="form-inline" name="deleteFilm" method="POST" action="controller"/>
                    <input type="hidden" name="command" value="DELETE_FILM">
                    <input type="hidden" name="filmId" value="${film.id}">
                    <button class="btn btn-success" type="submit"><fmt:message bundle="${pc}"
                                                                               key="label.delete"/></button>
                    </form>
                    </p>
                    </c:if>
                    </div>
            </li>
            <br>
        </c:forEach>
    </ul>
</c:if>

<ul class="list-unstyled">
    <c:forEach var="filmByName" items="${filmsListByName}" varStatus="Status">
        <li class="media">
            <img src="data:image/jpg;base64,${filmByName.poster}" class="mr-3" alt="POSTER" width="100" height="150">
            <div class="media-body">
            <h5 class="mt-0 mb-1"><a
                    href="${pageContext.request.contextPath}/controller?command=SHOW_FILM_INFO&filmId=${filmByName.id}">${filmByName.name}</a>
            </h5>
            <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.releaseDate"/>: <span
                    class="badge badge-secondary"><ctd:custom-date date="${film.releaseDate}"/></span></h8>
            <div class="dropdown ">
                <button class="btn btn-outline-success my-2 my-sm-0 dropdown-toggle" type="button"
                        id="dropdownMenuButton2"
                        data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    <fmt:message bundle="${pc}" key="label.filmRaiting"/> ${film.raiting}
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=1&filmId=${filmByName.id}">
                        1</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=2&filmId=${filmByName.id}">
                        2</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=3&filmId=${filmByName.id}">
                        3</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=4&filmId=${filmByName.id}">
                        4</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=5&filmId=${filmByName.id}">
                        5</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=6&filmId=${filmByName.id}">
                        6</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=7&filmId=${filmByName.id}">
                        7</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=8&filmId=${filmByName.id}">
                        8</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=9&filmId=${filmByName.id}">
                        9</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=COUNT_FILM_RAITING&filmRaiting=10&filmId=${filmByName.id}">
                        10</a>
                </div>
            </div>
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
