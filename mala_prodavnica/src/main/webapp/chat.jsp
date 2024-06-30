<%@ page import="com.example.mala_prodavnica.beans.UserBean" %>
<%@ page import="com.example.mala_prodavnica.services.ReceiptService" %>
<%@ page import="com.example.mala_prodavnica.model.Receipt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mala_prodavnica.model.ReceiptItem" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.rmi.Naming" %>
<%@ page import="com.example.mala_prodavnica.rmi.ICalculation" %>
<%@ page import="java.rmi.NotBoundException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.mala_prodavnica.beans.ReceiptBean" %>
<%@ page import="com.example.mala_prodavnica.beans.ReceiptItemBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserBean u = (UserBean) session.getAttribute("user");
    if(u==null)
    {
        response.sendRedirect("login.jsp");
    }
    else
    {
        ReceiptService receiptService = new ReceiptService();
        List<ReceiptBean> receipts = receiptService.getAllReceiptsByUser(u.getId());
        String message = "";
        if (receipts.isEmpty())
            message = "There are no purchases";
%>
<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" href="styles/chat.css">
</head>
<body>
<div id="menu">
    <ul>
        <li><a href="uputsvo.pdf" target="_blank">Help(?)</a></li>
        <li><a href="index.jsp">Home</a></li>
        <%
            if(!u.isAdmin())
            {
        %>
        <li><a href="receipts.jsp">Receipts</a></li>
        <%}%>
        <li><a href="chat.jsp">Chat</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</div>
<div class="container">
    <div class="chat">
        <h2>Chat</h2>
        <div class="chat-messages" id="chat-msgs"></div>
        <div>
            <input class="message-input" type="text" name="message" id="message" placeholder="Enter message here"/>
            <button class="button" onclick="sendMsg();">Send</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    const username = "<%= u.getUsername() %>";

    let wsUrl;
    if (window.location.protocol == 'http:') {
        wsUrl = 'ws://';
    } else {
        wsUrl = 'wss://';
    }
    const webSocket = new WebSocket(wsUrl + 'localhost:8080/mala_prodavnica_war_exploded/chat');

    webSocket.onopen = function(event) {
        console.log("WebSocket connection opened.");
    };

    webSocket.onmessage = function(event) {
        const data = event.data.split(":");
        const username = data[0];
        const message = data[1];

        const chatElement = document.getElementById("chat-msgs");
        const messageElement = document.createElement('div');

        if(username === "<%= u.getUsername() %>") {
            messageElement.className = "message-user";
            messageElement.innerText += "You:" + message;
        }
        else if(username === "Server") {
            messageElement.className = "message-server";
            messageElement.innerText += "Server:" +message;
        }
        else {
            messageElement.className = "message-other";
            messageElement.innerText += username + ":" +message;

        }

        chatElement.append(messageElement);
        chatElement.scrollTop = chatElement.scrollHeight;
    };


    webSocket.onclose = function(event) {
        console.log("WebSocket connection closed.");
    };

    webSocket.onerror = function(event) {
        console.log("Error", event);
    };

    function sendMsg() {
        const message = document.getElementById("message").value;
        if (message) {
            const completedMessage = username + ":" + message;
            webSocket.send(completedMessage);
        }
        document.getElementById("message").value = "";
    }
</script>
</html>
<%}%>
