package com.store.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StarterServlet {

    @GetMapping("/welcome")
    public String renderWelcomePage() {
        return "static/html/welcome.html";
    }
}
