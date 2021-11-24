package com.store.services;

import com.store.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService{
    List<Product> getAll();

    boolean delete(Product object);

    Optional<Product> getById(int id);

    boolean save(Product object);

    boolean update(Product object);

    List<Product> findByNameAndDescription(String productItem);
}
