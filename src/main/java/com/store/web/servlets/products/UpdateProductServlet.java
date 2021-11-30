package com.store.web.servlets.products;

import com.store.entities.Product;
import com.store.services.ProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@WebServlet("/products/update")
public class UpdateProductServlet extends HttpServlet {
    private ProductService service;

    @Override
    public void init() {
        service = (ProductService) getServletContext().getAttribute("ProductService");
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
        } else {
            response.getWriter().write("Sorry, but story doesn't contain product with such ID: " + request.getParameter("id"));
        }
    }
}
