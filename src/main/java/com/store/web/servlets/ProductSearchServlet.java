package com.store.web.servlets;

import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

public class ProductSearchServlet extends HttpServlet {
    private final ProductService service;

    public ProductSearchServlet(ProductService service) {
        this.service = service;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");

        List<Product> productList = service.findByNameAndDescription(search);
        String response = new Gson().toJson(productList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(response);
    }
}
