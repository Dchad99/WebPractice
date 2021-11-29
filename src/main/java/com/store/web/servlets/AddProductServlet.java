package com.store.web.servlets;

import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@WebServlet("/products/add")
public class AddProductServlet extends HttpServlet {
    private ProductService service;

    @Override
    public void init() {
        service = (ProductService) getServletContext().getAttribute("ProductService");
    }

    @Override
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Integer.parseInt(request.getParameter("price")));
        product.setDate(new Date());
        product.setDescription(request.getParameter("desc"));
        service.save(product);

        response.sendRedirect(request.getContextPath() + "/products");
    }

}
