<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<fmt:setLocale value="${sessionScope.language}"/>
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
        <form class="form-inline" name="main" method="GET" action="controller">
            <input type="hidden" name="command" value="passing_to_main"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message bundle="${pc}"
                                                                                            key="label.mainPage"/></button>
        </form>
    </div>
</nav>
<hr>
<li class="media">
    <div class="media-body">
        <h5 class="mt-0 mb-1">
            ${user.firstName} ${user.lastName}
        </h5>
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.birthday"/>: <span
                class="badge badge-secondary">${user.birthday}</span></h8>
        <c:if test="${user.getRoleType() eq 'ADMIN'}">
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.userRole"/>: <span
                class="badge badge-secondary"><br><fmt:message bundle="${pc}" key="label.admin"/></span>
        </h8>
        </c:if>
        <c:if test="${user.getRoleType() eq 'USER'}">
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.userRole"/>: <span
                class="badge badge-secondary"><br><fmt:message bundle="${pc}" key="label.user"/></span>
        </h8>
        </c:if>
        <c:if test="${user.getStatusType() eq 'ACTIVE'}">
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.userStatus"/>: <span
                class="badge badge-secondary"><br><fmt:message bundle="${pc}" key="label.active"/></span>
        </h8>
        </c:if>
        <c:if test="${user.getStatusType() eq 'INACTIVE'}">
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.userStatus"/>: <span
                class="badge badge-secondary"><br><fmt:message bundle="${pc}" key="label.inactive"/></span>
        </h8>
        </c:if>
        <h8 class="mt-0 mb-1"><br><fmt:message bundle="${pc}" key="label.login"/>: <span
                class="badge badge-secondary">${user.login}</span></h8>
    </div>
</li>
</body>
</html>