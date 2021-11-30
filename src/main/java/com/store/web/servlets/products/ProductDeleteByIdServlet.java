package com.store.web.servlets.products;

import com.store.entities.Product;
import com.store.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/products/delete")
public class ProductDeleteByIdServlet extends HttpServlet {
    private ProductService service;

    @Override
    public void init() {
        service = (ProductService) getServletContext().getAttribute("ProductService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Optional<Product> product = service.getById(productId);
        if (product.isPresent()) {
            Product deleteProduct = product.get();
            service.delete(deleteProduct);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.getWriter().write("Product with such id wasn't found");
        }
    }
}
