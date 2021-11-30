package com.store.services.impl;

import com.store.entities.Order;
import com.store.repositories.BasketRepository;
import com.store.services.BasketService;

import java.util.List;
import java.util.Optional;

public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;

    public BasketServiceImpl(BasketRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> getAllOrdersByUserId(int id) {
        return repository.getAllOrdersByUserId(id);
    }

    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Order order) {
        return repository.delete(order);
    }

    @Override
    public Optional<Order> getById(int id) {
        return repository.getById(id);
    }

    @Override
    public boolean save(Order order) {
        return repository.save(order);
    }

    @Override
    public boolean update(Order order) {
        return repository.update(order);
    }

    @Override
    public boolean deleteByProductId(int id) {
        return repository.deleteByProductId(id);
    }
}
