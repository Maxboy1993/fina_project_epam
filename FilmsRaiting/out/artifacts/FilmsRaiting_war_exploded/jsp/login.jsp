<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login"/>
    <br>Login:<br/>
    <input type="text" name="login" value=""/>
    <br>Password<br/>
    <input type="password" name="password" value=""/>
    <br>
    ${errorLoginPassMessage}
    <br/>
    <br>
    ${wrongAction}
    <br/>
    <br>
    ${nullPage}
    <br/>
    <input type="submit" value="log in"/>
</form><hr>
</body>
</html>
