package com.store.web.servlets;

import com.store.entities.User;
import com.store.services.SecurityService;
import com.store.services.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class LoginServlet extends HttpServlet {
    private final UserService service;
    private final SecurityService securityService;

    public LoginServlet(UserService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/login.html")
                .readAllBytes());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        final Optional<User> user = service.getRecordByParam(username);
        if (user.isPresent()) {
            User fromDb = user.get();
            final String encoded_password = securityService.encryptData(password + fromDb.getUserHash());
            if (Objects.equals(encoded_password, fromDb.getPassword())) {
                Cookie cookie = new Cookie("user-token", fromDb.getUserHash());
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                response.sendRedirect("/products");
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("/login");
    }

}
