package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.beans.UpdateProductBean;
import com.example.mala_prodavnica.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "updateProduct",value = "/updateProduct")
public class UpdateProductServlet extends HttpServlet {
    ProductService productService;
    public void init() {
        productService = new ProductService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateProductBean productBean = new UpdateProductBean();
        int prod_id = Integer.parseInt(req.getParameter("id"));
        String priceS = req.getParameter("price");
        String name = req.getParameter("name");
        if(name.isEmpty() || priceS.isEmpty())
        {
            resp.sendRedirect("index.jsp");
            return;
        }
        int price = Integer.parseInt(req.getParameter("price"));
        if(price <= 0)
        {
            resp.sendRedirect("index.jsp");
            return;
        }
        productBean.setProduct_id(prod_id);
        productBean.setName(name);
        productBean.setPrice(price);
        productService.updateProduct(productBean);
        resp.sendRedirect("index.jsp");

    }
    public void destroy() {
        productService = null;
    }
}
