<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18.06.2020
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<form name="signUp" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <br>First Name:<br/>
    <input required type="text" name="firstName" placeholder="first_name"/>
    <%--    <input type="text" name="firstName" value=""/>--%>
    <br>Last Name:<br/>
    <input required type="text" name="lasttName" placeholder="last_name"/>
    <%--    <input type="text" name="lasttName" value=""/>--%>
    <br>Login:<br/>
    <input required type="text" name="login" placeholder="login"/>
    <%--    <input type="text" name="login" value=""/>--%>
    <br>Password<br/>
    <%--    <input type="password" name="password" value=""/>--%>
    <input required type="password" name="password" placeholder="password"/>
    <br>Birthday<br/>
    <input required type="date" name="day" placeholder="day"/>
    ${errorLoginPassMessage}
    <br/>
    <input type="submit" value="sign up"/>
</form>
<hr>
</body>
</html>
