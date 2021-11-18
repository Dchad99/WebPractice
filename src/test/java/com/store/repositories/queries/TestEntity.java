package com.store.repositories.queries;

import lombok.Data;

import java.util.Date;

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

    @Column
    private Date date;

    public TestEntity() {
    }

    public TestEntity(int id, String name, int price, Date date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
