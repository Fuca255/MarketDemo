package com.example.mala_prodavnica.servlets;


import com.example.mala_prodavnica.beans.ProductBean;
import com.example.mala_prodavnica.model.Product;
import com.example.mala_prodavnica.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


@WebServlet(name = "addProduct", value = "/addProduct")
public class AddNewProduct extends HttpServlet {
    ProductService productService;
    public void init() {
        productService = new ProductService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String priceS = req.getParameter("price");
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
        ProductBean b = productService.createProduct(name,price);
        if(b==null)
            System.out.println("Greska: Proizvod nije uspesno napravljen");
        else
            resp.sendRedirect("index.jsp");
    }
    public void destroy() {
        productService= null;
    }
}
