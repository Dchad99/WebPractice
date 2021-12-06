package com.store.web.controllers.basket;

import com.google.gson.Gson;
import com.store.entities.Order;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.services.BasketService;
import com.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BasketServlet {
    private final BasketService basketService;
    private final ProductService productService;

    @GetMapping("/products/cart")
    public void displayProductFromBasket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    @PostMapping("/products/cart")
    public void addProductToBasket(HttpServletRequest req, HttpServletResponse resp) {
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

    @PostMapping("/products/cart/delete")
    public void deleteFromBasket(HttpServletRequest request, HttpServletResponse response){
        basketService.deleteByProductId(Integer.parseInt(request.getParameter("id")));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/cart")
    public String displayBasketPage() {
        return "static/html/basketTable.html";
    }

}
