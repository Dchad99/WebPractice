package com.store.web.servlets;

import com.store.entities.User;
import com.store.security.SecurityService;
import com.store.services.UserService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private final UserService service;
    private final SecurityService securityService;

    public RegisterServlet(UserService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
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

        final String unique_identifier = securityService.getRandomUUID();
        final String encoded_password = securityService.encryptData(password + unique_identifier);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoded_password);
        user.setUserHash(unique_identifier);

        service.save(user);
        response.sendRedirect(request.getContextPath() + "/login");
    }

}
