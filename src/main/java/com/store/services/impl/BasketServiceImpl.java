package com.store.services.impl;

import com.store.entities.Order;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.BasketRepository;
import com.store.services.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;

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
        Optional<Order> entity = repository.getById(order.getId());
        if (entity.isPresent()) {
            return repository.delete(order);
        }

        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Order wasn't found, where productId: " + order.getProductId());
    }

    @Override
    public Optional<Order> getById(int id) {
        Optional<Order> order = repository.getById(id);

        if (order.isPresent()) {
            return order;
        }
        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Order wasn't found, where productId: " + id);
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
