package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.services.ProductService;
import com.example.mala_prodavnica.services.ReceiptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "removeReceipt",value="/removeReceipt")
public class RemoveReceiptServlet extends HttpServlet {
    private ReceiptService receiptService;
    public void init() {
        receiptService = new ReceiptService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rec_id = Integer.parseInt(req.getParameter("id"));
        receiptService.deleteReceipt(rec_id);
        resp.sendRedirect("receipts.jsp");
    }
    public void destroy() {
        receiptService = null;
    }
}
