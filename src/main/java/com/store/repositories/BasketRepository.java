package com.store.repositories;

import com.store.entities.Order;
import com.store.repositories.db_config.jdbc.CrudRepository;
import java.util.List;

public interface BasketRepository extends CrudRepository<Order> {
    List<Order> getAllOrdersByUserId(int id);
    boolean deleteByProductId(int id);
}
