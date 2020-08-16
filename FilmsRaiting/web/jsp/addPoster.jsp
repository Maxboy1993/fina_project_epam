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
    <title><fmt:message bundle="${pc}" key="label.addPoster"/></title>
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
            <input type="hidden" name="command" value="passing_to_add_film">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.cancel"/></button>
        </form>
    </div>
</nav>
<br>
<c:if test="${not empty posterFormatError}">
    <fmt:message bundle="${pc}" key="${posterFormatError}"/>
</c:if>
<br>
<c:if test="${not empty posterAddingError}">
    <fmt:message bundle="${pc}" key="${posterAddingError}"/>
</c:if>
<div class="wrapper">
    <form class="form-signin" method="POST" action="uploadController" enctype="multipart/form-data">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.choosePoster"/></h5>
        <input required type="file" name="poster" size="10"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.addPoster"/></button>
        <button class="btn btn-lg btn-primary btn-block" type="reset"><fmt:message bundle="${pc}"
                                                                                   key="label.clear"/></button>
    </form>
</div>
</body>
</html>

