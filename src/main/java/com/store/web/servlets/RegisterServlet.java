package com.store.web.servlets;

import com.store.entities.User;
import com.store.security.SecurityService;
import com.store.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService service;
    private SecurityService securityService;

    @Override
    public void init() throws ServletException {
        service = (UserService) getServletContext().getAttribute("UserService");
        securityService = (SecurityService) getServletContext().getAttribute("SecurityService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        final String unique_identifier = securityService.getRandomUUID();
        final String encoded_password = securityService.encryptData(password + unique_identifier);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoded_password);
        user.setUserHash(unique_identifier);

        service.save(user);
        response.sendRedirect(request.getContextPath() + "/welcome");
    }

}
