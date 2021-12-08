package com.store.dto;

import lombok.Data;


@Data
public class ProductDTO {
    private int id;
    private String name;
    private Integer price;
    private String desc;
}
