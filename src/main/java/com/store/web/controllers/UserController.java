package com.store.web.controllers;

import com.store.entities.User;
import com.store.security.SecurityService;
import com.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService service;
    private final SecurityService securityService;

    @PostMapping("/register")
    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


    @PostMapping("/login")
    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        Optional<User> user = service.getByParam(username);

        if (user.isPresent()) {
            User fromDb = user.get();
            final String encoded_password = securityService.encryptData(password + fromDb.getUserHash());
            if (Objects.equals(encoded_password, fromDb.getPassword())) {
                Cookie cookie = new Cookie("user-token", fromDb.getUserHash());
                cookie.setMaxAge(15000);

                session.setAttribute("user", fromDb);

                response.addCookie(cookie);
                response.sendRedirect("/productPage");
            }
        } else {
            response.sendRedirect("/welcome");
        }
    }


    @GetMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
