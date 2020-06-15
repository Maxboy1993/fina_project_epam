<%@ page import="model.service.impl.UserServiceImpl" %>
<%@ page import="entity.User" %>
<%@ page import="model.dao.impl.UserDaoImpl" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.06.2020
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TestJsp</title>
</head>
<body>
<p>
    <% String name  =request.getParameter("name");%>
    <%=name%>
</p>
</body>
</html>
