package com.store.web.controllers.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.store.entities.Product;
import com.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ProductServlet {
    private final ProductService service;

    @GetMapping(path = "/productPage")
    public String getAllProductPage() {
        return "static/html/productTable.html";
    }

    @GetMapping("/products")
    @ResponseBody
    public String getAllProduct(HttpServletResponse response) {
        List<Product> productList = service.getAll();
        String resp = new Gson().toJson(productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        return resp;
    }


    @PostMapping("/products/update")
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @GetMapping("/products/search")
    public void searchProductHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String search = req.getParameter("search");

        List<Product> productList = service.findByNameAndDescription(search);
        String response = new Gson().toJson(productList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(response);
    }

    @PostMapping("/products/delete")
    protected void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Optional<Product> product = service.getById(productId);

        service.delete(product.get());
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

    }

    @GetMapping("/product")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Product> product = service.getById(id);
        if (product.isPresent()) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(product.get()));
        }
    }


    @PostMapping("/products/add")
    public void doPost(@RequestParam String name, @RequestParam Integer price, @RequestParam String desc, HttpServletResponse response) throws IOException {

        Product product = new Product();

        product.setName(name);
        product.setPrice(price);
        product.setDate(new Date());
        product.setDescription(desc);

        service.save(product);

        response.sendRedirect("/productPage");
    }


}
