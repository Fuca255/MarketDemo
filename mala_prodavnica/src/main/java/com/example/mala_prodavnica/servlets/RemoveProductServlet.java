package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "removeProduct",value="/removeProduct")
public class RemoveProductServlet extends HttpServlet {
    private ProductService productService;
    public void init() {
        productService = new ProductService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int prod_id = Integer.parseInt(req.getParameter("id"));
        productService.deleteProduct(prod_id);
        resp.sendRedirect("index.jsp");
    }
    public void destroy() {
        productService = null;
    }
}
