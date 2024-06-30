package com.example.mala_prodavnica.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "removeFromCart", value = "/removeFromCart")
public class RemoveFromCart extends HttpServlet {
    public void init() {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String key = req.getParameter("rmkey");
        HashMap<String,Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");

        cart.remove(key);
        session.setAttribute("cart",cart);
        resp.sendRedirect("index.jsp");
    }
    public void destroy() {
    }
}