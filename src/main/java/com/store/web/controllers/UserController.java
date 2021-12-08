package com.store.web.controllers;

import com.store.dto.Credential;
import com.store.entities.User;
import com.store.security.SecurityService;
import com.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String registerUser(@ModelAttribute Credential credential) {
        final String unique_identifier = securityService.getRandomUUID();
        final String encoded_password = securityService.encryptData(credential.getPassword() + unique_identifier);

        User user = new User(credential.getUsername(), encoded_password, unique_identifier);
        service.save(user);

        return "redirect:/welcome";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute Credential credential, HttpSession session, HttpServletResponse response) {
        Optional<User> user = service.getByParam(credential.getUsername());

        if (user.isPresent()) {
            User fromDb = user.get();
            final String encoded_password = securityService.encryptData(credential.getPassword() + fromDb.getUserHash());
            if (Objects.equals(encoded_password, fromDb.getPassword())) {
                Cookie cookie = new Cookie("user-token", fromDb.getUserHash());
                cookie.setMaxAge(15000);
                session.setAttribute("user", fromDb);
                response.addCookie(cookie);

                return "redirect:/productPage";
            }
        }
        return "redirect:/welcome";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Cookie readCookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("user-token")) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                readCookie = cookie;
            }
        }
        session.invalidate();
        resp.addCookie(readCookie);

        return "redirect:/welcome";
    }

}
