package com.store.entities;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Product {
    private int id;
    private String name;
    private int price;
    private LocalDate date;
}
