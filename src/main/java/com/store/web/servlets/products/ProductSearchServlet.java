package com.store.web.servlets.products;

import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

@WebServlet("/products/search")
public class ProductSearchServlet extends HttpServlet {
    private ProductService service;

    @Override
    public void init() throws ServletException {
        service = (ProductService) getServletContext().getAttribute("ProductService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String search = req.getParameter("search");

        List<Product> productList = service.findByNameAndDescription(search);
        String response = new Gson().toJson(productList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(response);
    }
}
