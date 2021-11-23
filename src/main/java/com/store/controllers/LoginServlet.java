package com.store.controllers;

import com.store.entities.User;
import com.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
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

    public LoginServlet(UserService service) {
        this.service = service;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            final Optional<User> user = service.getRecordByParam(username);
            if (user.isPresent()) {
                User fromDb = user.get();
                final String encoded_password = DigestUtils.md5Hex(password + fromDb.getUserHash());
                if (Objects.equals(encoded_password, fromDb.getPassword())) {
                    Cookie cookie = new Cookie("user-token", fromDb.getUserHash());
                    response.addCookie(cookie);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.sendRedirect("/products");
                }
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/login");
        } catch (IOException e) {
            log.warn("Internal server error", e);
        }
    }

}
