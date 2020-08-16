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
    <title><fmt:message bundle="${pc}" key="label.filmInfo"/></title>
    <link href="${pageContext.request.contextPath}/css/table.scss" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="main" method="GET" action="controller">
            <input type="hidden" name="command" value="passing_to_main"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                            key="label.mainPage"/></button>
        </form>
    </div>
</nav>
<hr>
<div class="media">
    <img class="align-self-center mr-3" src="data:image/jpg;base64,${film.poster}" alt="Generic placeholder image">
    <div class="media-body">
        <h5 class="mt-0">${film.name}</h5>
        <p class="mt-0">
            <h7><fmt:message bundle="${pc}" key="label.filmRaiting"/> <span
                    class="badge badge-secondary"> ${film.raiting}</span></h7>
        </p>
        <p class="mt-0">
            <h7><fmt:message bundle="${pc}" key="label.filmRaiting"/> <span
                    class="badge badge-secondary"> <ctd:custom-date date="${film.releaseDate}"/></span></h7>
        </p>
        <p class="mt-0">
            <h7></h7>
        </p>
        </p>
        <p class="mt-0">
            <h7><fmt:message bundle="${pc}" key="label.director"/> :<span
                    class="badge badge-secondary"> ${film.director.firstName} ${film.director.lastName}</span></h7>
            <h7><fmt:message bundle="${pc}" key="label.birthday"/> :
                <br>
                <span class="badge badge-secondary"> <ctd:custom-date date="${film.director.birthday}"/></span></h7>
        </p>
        <p class="mt-0">
            <h7><fmt:message bundle="${pc}" key="label.actor"/> :</h7>
            <br>
            <c:forEach var="actor" items="${film.actors}" varStatus="Status">
                <h7><span class="badge badge-secondary"> ${actor.firstName} ${actor.lastName}</span></h7>
                <h7><fmt:message bundle="${pc}" key="label.birthday"/> : <span
                        class="badge badge-secondary"> <ctd:custom-date date="${actor.birthday}"/></span></h7>
                <br>
            </c:forEach>
        </p>
    </div>
</div>
<br>
<form name="addReview" method="POST" action="controller">
    <div class="form-group">
        <input type="hidden" class="form-group" name="command" value="ADD_REVIEW"/>
        <input type="hidden" class="form-group" name="filmId" value="${filmId}"/>
        <input type="text" class="form-group" name="review" id="exampleInputEmail1" size="50"
               placeholder="<fmt:message bundle="${pc}" key="label.review"/>">
        <button type="submit" class="btn btn-primary"><fmt:message bundle="${pc}" key="label.addReview"/></button>
    </div>
</form>

<c:if test="${ not empty errorReviewAdding}">
    <fmt:message bundle="${pc}" key="${errorReviewAdding}"/>
</c:if>

<c:set var="reviewVar" value="${reviewList}"/>
<c:set var="userVar" value="${userList}"/>
<div class="container">
    <h5><fmt:message bundle="${pc}" key="label.reviews"/>:</h5>
    <table class="responsive-table">
        <tr class="table-header">
            <th class="col col-1"><fmt:message bundle="${pc}" key="label.firstName"/></th>
            <th class="col col-2"><fmt:message bundle="${pc}" key="label.review"/></th>
            <th class="col col-3"><fmt:message bundle="${pc}" key="label.reviewDate"/></th>
        </tr>
        <c:forEach var="review" items="${reviewList}" varStatus="Status">
            <tr class="table-row">
                <c:forEach var="user" items="${userList}" varStatus="Status">
                    <c:if test="${review.userId eq user.id}">
                        <td class="col col-1"
                            data-label="<fmt:message bundle="${pc}" key="label.firstName"/>">${user.firstName}</td>
                    </c:if>
                </c:forEach>
                <td class="col col-2"
                    data-label="<fmt:message bundle="${pc}" key="label.review"/>">${review.review}</td>
                <td class="col col-3"
                    data-label="<fmt:message bundle="${pc}" key="label.reviewDate"/>"> <ctd:custom-date date="${review.reviewDate}"/></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
            <c:if test="${userRole eq 'ADMIN'}">
                <form class="form-inline" name="deleteReview" method="POST" action="controller"/>
                <input type="hidden" name="command" value="DELETE_REVIEW">
                <input type="hidden" name="reviewId" value="${review.id}">
                <button class="btn btn-success" type="submit"><fmt:message bundle="${pc}"
                                                                           key="label.delete"/></button>
                </form>
            </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

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
