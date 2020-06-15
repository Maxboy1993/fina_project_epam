<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.06.2020
  Time: 09:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome</h1>
<hr>
${user}, hello!
</hr>
<form name="login" method="POST" action="controller"/>
<input type="hidden" name="command" value="logout">
<br/>
<input type="submit" value="logout">
</form>
</body>
</html>
