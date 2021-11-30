package com.store.web.servlets.basket;

import com.google.gson.Gson;
import com.store.entities.Order;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.services.BasketService;
import com.store.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/products/cart")
public class BasketServlet extends HttpServlet {
    private BasketService basketService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        basketService = (BasketService) getServletContext().getAttribute("BasketService");
        productService = (ProductService) getServletContext().getAttribute("ProductService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user != null) {
            List<Product> orderedProduct = new ArrayList<>();
            List<Order> productList = basketService.getAllOrdersByUserId(user.getId());

            for (Order order : productList) {
                Optional<Product> product = productService.getById(order.getProductId());
                product.ifPresent(orderedProduct::add);
            }

            String response = new Gson().toJson(orderedProduct);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Optional<Product> product = productService.getById(productId);
        if(product.isPresent()){
            Product data = product.get();
            Order order = new Order();
            order.setProductId(data.getId());
            order.setClientId(user.getId());
            basketService.save(order);
        }
    }
}
