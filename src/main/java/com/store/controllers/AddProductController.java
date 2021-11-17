package com.store.controllers;

import com.store.entities.Product;
import com.store.services.ProductService;
import com.store.templater.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddProductController extends HttpServlet {
    private final ProductService service;

    public AddProductController(ProductService service) {
        this.service = service;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.instance().getPage("addProduct.html", null));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();

        product.setName(request.getParameter("name"));
        product.setPrice(Integer.parseInt(request.getParameter("price")));
        product.setDate(Date.valueOf(LocalDate.now()));

        if(!service.save(product)){
            response.getWriter().write("Product wasn't added");
        } else {
            response.getWriter().write("Product was added");
        }
    }

}
