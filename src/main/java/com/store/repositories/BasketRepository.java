package com.store.repositories;

import com.store.entities.Order;
import com.store.repositories.db_config.jdbc.CrudRepository;

public interface BasketRepository extends CrudRepository<Order> {
}
