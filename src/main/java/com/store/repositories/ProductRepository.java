package com.store.repositories;

import com.store.entities.Product;
import com.store.repositories.db_config.jdbc.CrudRepository;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product> {
    List<Product> findByNameAndDescription(String productName, String productDescription);
}
