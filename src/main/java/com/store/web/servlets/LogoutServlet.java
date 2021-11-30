package com.store.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cookie readCookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equalsIgnoreCase("user-token")){
                cookie.setValue("");
                cookie.setMaxAge(0);
                readCookie = cookie;
            }
        }

        session.invalidate();

        resp.addCookie(readCookie);
        resp.sendRedirect(req.getContextPath() + "/welcome");
    }
}
