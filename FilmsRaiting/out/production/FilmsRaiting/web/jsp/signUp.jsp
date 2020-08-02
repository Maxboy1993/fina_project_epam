<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.signup_btn"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="signIn" method="GET" action="controller">
            <input type="hidden" name="command" value="passing_to_sign_in"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                            key="label.signIn_btn"/></button>
        </form>
    </div>
</nav>

<br>
<c:if test="${not empty errorUserValidationMessage}">
    <c:forEach var="errorUserValidation" items="${errorUserValidationMessage}">
        <fmt:message bundle="${pc}" key="${errorUserValidation}"/>
        <br>
    </c:forEach>
</c:if>

<div class="wrapper">
    <form class="form-signin" name="signUp" method="POST" action="controller">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.signup_btn"/></h5>
        <input type="hidden" name="command" value="sign_up"/>
        <input required type="text" class="form-control" autofocus="" name="firstName"
               placeholder="<fmt:message bundle="${pc}" key="label.firstName"/>"
               required pattern="[a-zA-Z\s]{2,50}" title="First Name format is incorrect"/>
        <input required type="text" class="form-control" autofocus="" name="lastName"
               placeholder="<fmt:message bundle="${pc}" key="label.lastNmae"/>"
               required pattern="[a-zA-Z\s]{2,50}" title="Last Name format is incorrect"/>
        <input required type="text" class="form-control" autofocus="" name="login"
               placeholder="<fmt:message bundle="${pc}" key="label.login"/>(e-mail)"
               required pattern="[a-zA-Z0-9_.-]{1,40}@[a-zA-Z0-9_-]{2,40}\.[a-z]{2,4}"
               title="Login format is incorrect"/>
        <input type="password" class="form-control" autofocus="" name="password"
               placeholder="<fmt:message bundle="${pc}" key="label.password"/>"
               required pattern="[a-zA-Z0-9_.-@]{6,50}" title="Password format is incorrect"/>
        <input required type="date" class="form-control" autofocus="" name="birthday"
               placeholder="<fmt:message bundle="${pc}" key="label.birthday"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.signup_btn"/></button>
    </form>
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
