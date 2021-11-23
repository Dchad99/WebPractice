package com.store.controllers;

import com.store.entities.User;
import com.store.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RegisterServlet extends HttpServlet {
    private final UserService service;

    public RegisterServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/register.html")
                .readAllBytes());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        final String unique_identifier = UUID.randomUUID().toString();
        final String encoded_password = DigestUtils.md5Hex(password + unique_identifier);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoded_password);
        user.setUserHash(unique_identifier);

        service.save(user);

        response.sendRedirect(request.getContextPath() + "/login");
    }

}
