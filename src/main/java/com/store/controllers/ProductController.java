package com.store.controllers;

import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.SneakyThrows;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductController extends HttpServlet {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Product> productList = service.getAll();

        String resp = new Gson().toJson(productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resp);
    }

}
