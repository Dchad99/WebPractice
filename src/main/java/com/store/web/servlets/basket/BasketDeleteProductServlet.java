package com.store.web.servlets.basket;

import com.store.services.BasketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/cart/delete")
public class BasketDeleteProductServlet extends HttpServlet {
    private BasketService basketService;

    @Override
    public void init() throws ServletException {
        basketService = (BasketService) getServletContext().getAttribute("BasketService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        basketService.deleteByProductId(id);
    }

}
