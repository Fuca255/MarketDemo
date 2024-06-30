package com.example.mala_prodavnica.servlets;

import com.example.mala_prodavnica.beans.ProductBean;
import com.example.mala_prodavnica.beans.UserBean;
import com.example.mala_prodavnica.interfaces.IUserRepository;
import com.example.mala_prodavnica.model.User;
import com.example.mala_prodavnica.repository.UserRepository;
import com.example.mala_prodavnica.services.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;


@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    public void init() {
        userService = new UserService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username =  request.getParameter("username");
        String password = request.getParameter("password");
        String message = "";
        if(username==null || password==null)
        {
            message = "login form not filled correctly";
            response.sendRedirect("login.jsp?message="+message);
            return;
        }
        Optional<UserBean> userBean = userService.login(username,password);
        if(userBean.isEmpty())
        {
            message = "Incorrect username or password";
            response.sendRedirect("login.jsp?message="+message);
            return;
        }
        HttpSession session = request.getSession(true);
        System.out.println(userBean.get().getMoney());
        session.setAttribute("user",userBean.get());
        if(session.getAttribute("cart")==null)
            session.setAttribute("cart",new HashMap<String,Integer>());
        response.sendRedirect("index.jsp");
    }

    public void destroy() {
        userService = null;
    }
}
