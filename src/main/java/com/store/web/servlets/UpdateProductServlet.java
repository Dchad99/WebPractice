package com.store.web.servlets;

import com.store.entities.Product;
import com.store.services.ProductService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class UpdateProductServlet extends HttpServlet {
    private final ProductService service;

    public UpdateProductServlet(ProductService service) {
        this.service = service;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(this.getClass()
                .getClassLoader()
                .getResourceAsStream("templates/update.html")
                .readAllBytes());
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Product> productToUpdate = service.getById(id);
        if (productToUpdate.isPresent()) {
            Product p = productToUpdate.get();
            p.setName(request.getParameter("name"));
            p.setPrice(Integer.parseInt(request.getParameter("price")));
            p.setDate(new Date());
            p.setDescription(request.getParameter("desc"));
            service.update(p);
            response.sendRedirect("/products");
        } else {
            response.getWriter().write("Sorry, but story doesn't contain product with such ID: " + request.getParameter("id"));
        }
    }
}
