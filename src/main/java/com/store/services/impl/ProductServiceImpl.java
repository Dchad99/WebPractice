package com.store.services.impl;

import com.store.entities.Product;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.ProductRepository;
import com.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(Product object) {
        Optional<Product> entity = repository.getById(object.getId());

        if(entity.isPresent()){
            return repository.delete(entity.get());
        }
        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Product wasn't found with id:" + object.getId());
    }

    @Override
    public Optional<Product> getById(int id) {
        Optional<Product> product = repository.getById(id);

        if (product.isPresent()) {
            return product;
        }

        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Product wasn't found with id: " + id);
    }

    @Override
    public boolean save(Product object) {
        return repository.save(object);
    }

    @Override
    public boolean update(Product object) {
        return repository.update(object);
    }

    @Override
    public List<Product> findByNameAndDescription(String productName) {
        return repository.findByNameAndDescription(productName);
    }
}
