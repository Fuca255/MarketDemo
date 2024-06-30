package com.example.mala_prodavnica.servlets;


import com.example.mala_prodavnica.model.User;
import com.example.mala_prodavnica.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;
    public void init(){
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confPassword = req.getParameter("confirm-password");
        String message = "";
        if(username==null || password==null || confPassword==null)
        {
            message = "Form not filled properly";
            resp.sendRedirect("register.jsp?message="+message);
            return;
        }
        if(!password.equals(confPassword))
        {
            message = "Passwords do not match";
            resp.sendRedirect("register.jsp?message="+message);
            return;
        }
        Pattern pattern = Pattern.compile("^[A-Z]{1}[a-zA-Z]{3}");
        Matcher matcher = pattern.matcher(username);
        boolean matchFound = matcher.find();
        if(!matchFound) {
            message = "Username must start with a Capital letter, and username has to have at least 4 letters";
            resp.sendRedirect("register.jsp?message="+message);
            return;
        }
        pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,}$");
        matcher = pattern.matcher(password);
        matchFound = matcher.find();
        if(!matchFound) {
            message = "Password must have at least one Capital letter, one number and it must be at least 6 characters long";
            resp.sendRedirect("register.jsp?message="+message);
            return;
        }

        boolean success =  userService.register(username,password);
        if(!success)
        {
            message = "That username already exists";
            resp.sendRedirect("register.jsp?message="+message);
            return;
        }
        resp.sendRedirect("login.jsp");

    }

    @Override
    public void destroy() {
        userService = null;
    }
}
