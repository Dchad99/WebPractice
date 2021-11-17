package com.store.services;

import com.store.entities.Product;
import com.store.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Product object) {
        return repository.delete(object);
    }

    @Override
    public Optional<Product> getById(int id) {
        return Optional.of(repository.getById(id)).orElse(Optional.empty());
    }

    @Override
    public boolean save(Product object) {
        return repository.save(object);
    }

    @Override
    public boolean update(Product object) {
        return repository.update(object);
    }
}
