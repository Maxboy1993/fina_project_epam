<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pagecontent.pagecontent" var="pc"/>

<html>
<head>
    <title><fmt:message bundle="${pc}" key="label.signIn_btn"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <div class="form-inline">
        <form class="form-inline" name="cancelAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_sign_in">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.signIn_btn"/></button>
        </form>
    </div>
    <div class="form-inline">
        <table class="responsive-table">
            <tr class="table-header">
                <th class="col col-1"><fmt:message bundle="${pc}" key="label.errorCode"/>: 404</th>
            </tr>
            <tr class="table-row">
                <td class="col col-1"
                ><fmt:message bundle="${pc}" key="label.errorMessage404"/></td>
            </tr>
        </table>
    </div>
    <div class="form-inline">
        <form class="form-inline" name="continueAdding" action="controller" method="GET">
            <input type="hidden" name="command" value="passing_to_sign_up">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message bundle="${pc}"
                                                                                        key="label.signup_btn"/></button>
        </form>
    </div>
</nav>
</body>
</html>
