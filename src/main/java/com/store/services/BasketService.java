package com.store.services;

import com.store.entities.Order;

import java.util.List;
import java.util.Optional;

public interface BasketService {
    List<Order> getAllOrdersByUserId(int id);
    List<Order> getAll();
    boolean delete(Order order);
    Optional<Order> getById(int id);
    boolean save(Order order);
    boolean update(Order order);
    boolean deleteByProductId(int id);
}
