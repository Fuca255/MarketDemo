<%--
  Created by IntelliJ IDEA.
  User: korisnik
  Date: 6/28/2024
  Time: 9:22 PM
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
    <title>Register</title>
    <link rel="stylesheet" href="styles/register.css">
</head>
<body>
<div class="register-container">
    <h2>Register an Account</h2>
    <form action="register" method="post">

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <label for="confirm-password">Confirm Password:</label>
        <input type="password" id="confirm-password" name="confirm-password" required>
        <p style = 'color:red;'><%=message%></p>
        <p>Already have an account? <a href="login.jsp">Login here</a>.</p>
        <p>Need help? Click <a href="uputsvo.pdf" target="_blank">here</a>.</p>
        <input type="submit" name="register" value="Register">
    </form>
</div>
</body>
</html>
