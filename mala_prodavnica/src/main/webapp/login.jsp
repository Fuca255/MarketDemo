<%--
  Created by IntelliJ IDEA.
  User: korisnik
  Date: 6/28/2024
  Time: 7:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    if(session.getAttribute("user")!=null)
    {
        response.sendRedirect("index.jsp");
    }
    String message = request.getParameter("message");
    if(message==null)
        message = "";
%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <p style='color:red;'><%=message%></p>
        <p>Don't have an account? <a href="register.jsp">Register here</a>.</p>
        <p>Need help? Click <a href="uputsvo.pdf" target="_blank">here</a>.</p>
        <input type="submit" name="login" value="Login">
    </form>
</div>
</body>
</html>
