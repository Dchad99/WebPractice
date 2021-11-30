package com.store.entities;

import com.store.repositories.db_config.queries.Column;
import com.store.repositories.db_config.queries.Entity;
import com.store.repositories.db_config.queries.Id;
import lombok.Data;

@Data
@Entity(table = "orders")
public class Order {
    @Id
    @Column
    private int id;

    @Column
    private int clientId;

    @Column
    private int productId;

}
