package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.beans.ProductBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "addToCart", value = "/addToCart")
public class AddToCartServlet extends HttpServlet {
    public void init() {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int price =  Integer.parseInt(req.getParameter("price"));
        int id =  Integer.parseInt(req.getParameter("id"));
        String name =  req.getParameter("name");
        String key = name + "////" + id + "////" + price;
        HashMap<String,Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
        if(cart.containsKey(key))
            cart.put(key, cart.get(key) + 1);
        else
            cart.put(key,1);
        session.setAttribute("cart",cart);
        resp.sendRedirect("index.jsp");
    }
    public void destroy() {
    }
}
