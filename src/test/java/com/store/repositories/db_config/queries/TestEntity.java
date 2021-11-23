package com.store.repositories.db_config.queries;

import lombok.Data;

@Data
@Entity(table="products")
public class TestEntity {

    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private int price;


    public TestEntity(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
