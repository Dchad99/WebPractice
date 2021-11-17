package com.store.controllers;

import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(resp);
    }

    @SneakyThrows
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        Optional<Product> product = service.getById(productId);
        if (product.isPresent()) {
            Product deleteProduct = product.get();
            service.delete(deleteProduct);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().write("Product was deleted");
        } else {
            response.getWriter().write("Product with such id wasn't found");
        }
    }


}
