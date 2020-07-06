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
    <br>Birthday Day<br/>
    <input required type="number" name="day" placeholder="day"/>
    <br>Birthday Month<br/>
    <input required type="number" name="month" placeholder="month"/>
    <br>Birthday Year<br/>
    <input required type="number" name="year" placeholder="year"/>
<%--    <input type="number" name="day" value=""/>--%>
<%--    <input type="number" name="month" value=""/>--%>
<%--    <input type="number" name="year" value=""/>--%>
    <br>
    ${errorLoginPassMessage}
    <br/>
    <br>
    ${wrongAction}
    <br/>
    <br>
    ${nullPage}
    <br/>
    <input type="submit" value="sign up"/>
</form><hr>
</body>
</html>
