package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.beans.CreateReceiptBean;
import com.example.mala_prodavnica.beans.ReceiptItemBean;
import com.example.mala_prodavnica.beans.UserBean;
import com.example.mala_prodavnica.model.Receipt;
import com.example.mala_prodavnica.model.ReceiptItem;
import com.example.mala_prodavnica.services.ReceiptService;
import com.example.mala_prodavnica.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "buyItems", value = "/buyItems")
public class BuyItemsServlet extends HttpServlet {
    private ReceiptService receiptService;
    private UserService userService;
    public void init() {
        receiptService = new ReceiptService();
        userService = new UserService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int total =  Integer.parseInt(req.getParameter("total"));
        UserBean user = (UserBean) session.getAttribute("user");
        Date date = new Date();
        if(total > user.getMoney())
        {
            String message = "You dont have enough money to buy items in the cart";
            resp.sendRedirect("index.jsp?message="+message);
            return;
        }
        user.setMoney(user.getMoney() - total);
        userService.updateUserMoney(user);
        session.setAttribute("user",user);

        CreateReceiptBean createReceiptBean = new CreateReceiptBean();

        HashMap<String,Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
        List<ReceiptItemBean> items =  new ArrayList<>();


        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String[] keys = entry.getKey().split("////");
            int quantity = entry.getValue();

            ReceiptItemBean item = new ReceiptItemBean();
            item.setQuantity(quantity);
            item.setProduct_id(Integer.parseInt(keys[1]));
            items.add(item);
        }
        createReceiptBean.setItems(items);
        createReceiptBean.setTotal(total);
        createReceiptBean.setPurchaseDate(date);
        createReceiptBean.setUserId(user.getId());
        Receipt r = receiptService.createReceipt(createReceiptBean);
        if(r!=null) {
            cart.clear();
            session.setAttribute("cart", cart);
            resp.sendRedirect("index.jsp");
        }
        else
        {
            System.out.println("greska");
        }
    }
    public void destroy() {
        receiptService = null;
        userService = null;
    }
}
