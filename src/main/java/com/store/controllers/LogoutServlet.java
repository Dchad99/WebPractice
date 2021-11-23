package com.store.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie readCookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equalsIgnoreCase("user-token")){
                cookie.setValue("");
                cookie.setMaxAge(0);
                readCookie = cookie;
            }
        }
        resp.addCookie(readCookie);
        resp.sendRedirect("/login");
    }
}
