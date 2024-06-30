<%@ page import="java.util.List" %>
<%@ page import="com.example.mala_prodavnica.beans.UserBean" %>
<%@ page import="com.example.mala_prodavnica.services.ProductService" %>
<%@ page import="com.example.mala_prodavnica.beans.ProductBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    UserBean u = (UserBean) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
    } else {
        ProductService productService = new ProductService();
        List<ProductBean> products = productService.getAllProducts();
        String message = request.getParameter("message");
        if (message == null)
            message = "";
        HashMap<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="styles/home.css">
</head>
<body>
<div id="menu">
    <ul>
        <li><a href="uputsvo.pdf" target="_blank">Help(?)</a></li>
        <li><a href="index.jsp">Home</a></li>
        <% if (!u.isAdmin()) { %>
        <li><a href="receipts.jsp">Receipts</a></li>
        <% } %>
        <li><a href="chat.jsp">Chat</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</div>

<div class="user-info">
    <p>Welcome to our store, <%= u.getUsername() %></p>
    <% if (!u.isAdmin()) { %>
    <p>You currently have <%= u.getMoney() %> din</p>
    <% } %>
</div>

<div class="container">
    <div class="product_wrapper">
        <h1>Products</h1>
        <div class="products">
            <% for (ProductBean product : products) { %>
            <% if (u.isAdmin()) { %>
            <div class="product admin-product">
                <form action="updateProduct" method="POST">
                    <input type="hidden" name="id" value="<%= product.getId() %>">
                    Product Name: <input type="text" name="name" value="<%= product.getName() %>">
                    Price: <input type="number" name="price" value="<%= product.getPrice() %>">
                    <button type="submit">Update Item</button>
                </form>
                <form action="removeProduct" method="post">
                    <input type="hidden" name="id" value="<%= product.getId() %>">
                    <button type="submit">Remove item</button>
                </form>
            </div>
            <% } else { %>
            <div class="product">
                <form action="addToCart" method="POST">
                    <input type="hidden" name="id" value="<%= product.getId() %>">
                    <input type="hidden" name="name" value="<%= product.getName() %>">
                    <input type="hidden" name="price" value="<%= product.getPrice() %>">
                    <h2><%= product.getName() %></h2>
                    <p>Price: <%= product.getPrice() %> din</p>
                    <button type="submit">Add to cart</button>
                </form>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>

    <% if (u.isAdmin()) { %>
    <div class="addProductForm">
        <form action="addProduct" method="post">
            Product Name: <input type="text" name="name"><br>
            Product Price: <input type="number" name="price"><br>
            <button type="submit">Add new product</button>
        </form>
    </div>
    <% } else { %>
    <div class="cart">
        <h2>My Cart</h2>
        <div class="items">
            <% int total = 0; %>
            <% for (Map.Entry<String, Integer> entry : cart.entrySet()) { %>
            <% String[] keys = entry.getKey().split("////"); %>
            <% int quantity = entry.getValue(); %>
            <% total += quantity * Integer.parseInt(keys[2]); %>
            <form action="removeFromCart" method="post">
                <div class="item">
                    <input type="hidden" name="rmkey" value="<%= entry.getKey() %>">
                    <p><%= keys[0] %></p>
                    <span><%= quantity %></span>
                    <button type="submit">X</button>
                </div>
            </form>
            <% } %>
            <form action="buyItems" method="post">
                <input type="hidden" name="total" value="<%= total %>">
                <h3>Total: <%= total %></h3>
                <% if (!cart.isEmpty()) { %>
                <button class="buy-button" type="submit">Buy</button>
                <% } else { %>
                <span style="color: red;">No items in the cart</span>
                <% } %>
                <span style="color: red;"><%= message %></span>
            </form>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
<% } %>
