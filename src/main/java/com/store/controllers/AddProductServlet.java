package com.store.controllers;

import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.SneakyThrows;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AddProductServlet extends HttpServlet {
    private final ProductService service;

    public AddProductServlet(ProductService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isAuth = false;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equalsIgnoreCase("user-token")){
                    isAuth = true;
                }
            }
        }

        if(isAuth) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().write(this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("templates/addProduct.html")
                    .readAllBytes());
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Integer.parseInt(request.getParameter("price")));
        product.setDate(new Date());

        boolean status = service.save(product);
        response.sendRedirect("/products");
    }

}
