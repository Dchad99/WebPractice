package com.store.web.servlets;

import com.store.entities.User;
import com.store.security.SecurityService;
import com.store.services.UserService;
import com.store.web.util.ResourceParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;
    private SecurityService securityService;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("UserService");
        securityService = (SecurityService) getServletContext().getAttribute("SecurityService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        final Optional<User> user = service.getByParam(username);
        if (user.isPresent()) {
            User fromDb = user.get();
            final String encoded_password = securityService.encryptData(password + fromDb.getUserHash());
            if (Objects.equals(encoded_password, fromDb.getPassword())) {
                Cookie cookie = new Cookie("user-token", fromDb.getUserHash());
                cookie.setMaxAge(15000);
                response.addCookie(cookie);
                response.sendRedirect("/web-store/products");
            }
        } else {
            response.sendRedirect("/welcome");
        }
    }

}
