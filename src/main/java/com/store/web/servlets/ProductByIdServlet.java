package com.store.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet("/product")
public class ProductByIdServlet extends HttpServlet {
    private ProductService service;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() {
        service = (ProductService) getServletContext().getAttribute("ProductService");
    }

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Product> product = service.getById(id);
        if (product.isPresent()) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(product.get()));
        }
    }
}
