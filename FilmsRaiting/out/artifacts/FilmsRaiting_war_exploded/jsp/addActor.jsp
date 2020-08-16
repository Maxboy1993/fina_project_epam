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
    <title><fmt:message bundle="${pc}" key="label.addActor"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}/css/signIn.scss" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/table.scss" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="cancelAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_main">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.cancel"/></button>
        </form>
    </div>
    <div class="form-inline">
        <form class="form-inline" name="continueAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_add_director">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.addDirector"/></button>
        </form>
    </div>
</nav>
<br>
<c:if test="${not empty actorAddingError}">
    <fmt:message bundle="${pc}" key="${actorAddingError}"/>
</c:if>
<div class="wrapper">
    <form class="form-signin" name="addActor" action="controller" method="POST">
        <input type="hidden" name="command" value="add_actor">
        <h5 class="form-signin-heading"><fmt:message bundle="${pc}" key="label.inputActorData"/></h5>
        <input type="text" class="form-control" name="firstName"
               placeholder="<fmt:message bundle="${pc}" key="label.firstName"/>"
               required pattern="[a-zA-Z\s]{2,30}" title="First Name format is incorrect"/>
        <input type="text" class="form-control" name="lastName"
               placeholder="<fmt:message bundle="${pc}" key="label.lastNmae"/>"
               required pattern="[a-zA-Z\s]{2,30}" title="Last Name format is incorrect"/>
        <input type="date" class="form-control" name="birthday"
               placeholder="<fmt:message bundle="${pc}" key="label.birthday"/>"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                    key="label.addActor"/></button>
    </form>
</div>

<div class="container">
    <h5><fmt:message bundle="${pc}" key="label.actor"/>:</h5>
    <table class="responsive-table">
        <tr class="table-header">
            <th class="col col-1"><fmt:message bundle="${pc}" key="label.firstName"/></th>
            <th class="col col-2"><fmt:message bundle="${pc}" key="label.lastNmae"/></th>
            <th class="col col-3"><fmt:message bundle="${pc}" key="label.birthday"/></th>
        </tr>
        <c:forEach var="actor" items="${actors}" varStatus="Status">
            <tr class="table-row">
                <td class="col col-1"
                    data-label="<fmt:message bundle="${pc}" key="label.firstName"/>">${actor.firstName}</td>
                <td class="col col-2"
                    data-label="<fmt:message bundle="${pc}" key="label.lastNmae"/>">${actor.lastName}</td>
                <td class="col col-3"
                    data-label="<fmt:message bundle="${pc}" key="label.birthday"/>">${actor.birthday}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<hr>
</body>
</html>

