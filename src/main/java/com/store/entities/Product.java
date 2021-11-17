package com.store.entities;

import com.store.repositories.queries.Column;
import com.store.repositories.queries.Entity;
import com.store.repositories.queries.Id;
import java.sql.Date;


@Entity(table = "products")
public class Product {
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
