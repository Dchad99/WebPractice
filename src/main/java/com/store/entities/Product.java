package com.store.entities;

import com.store.repositories.queries.Column;
import com.store.repositories.queries.Entity;
import com.store.repositories.queries.Id;
import lombok.Data;
import java.util.Date;

@Data
@Entity(table = "products")
public class Product {

    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private Date date;

    public Product() {
    }

    public Product(int id, String name, int price, Date date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
