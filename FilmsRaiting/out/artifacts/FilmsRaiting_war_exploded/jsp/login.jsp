<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login"/>
    <input required type="text" name="login" placeholder="login"/>
    <input required type="password" name="password" placeholder="password"/>
<%--    <br>Login:<br/>--%>
<%--    <input type="text" name="login" value=""/>--%>
<%--    <br>Password<br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br>--%>
    ${errorLoginPassMessage}
    <br/>
    <input type="submit" value="log in"/>
</form><hr>
<form name="login" method="POST" action="controller">
    <input type="hidden" name="command" value="sign_up"/>
    <input type="submit" value="sign up"/>
</form><hr>
</body>
</html>
