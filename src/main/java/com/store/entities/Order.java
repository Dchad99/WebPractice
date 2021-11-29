package com.store.entities;

import com.store.repositories.db_config.queries.Column;
import com.store.repositories.db_config.queries.Entity;
import com.store.repositories.db_config.queries.Id;

@Entity(table = "orders")
public class Order {
    @Id
    @Column
    private int id;

    @Column
    private int user_id;

    @Column
    private int product_id;

}
