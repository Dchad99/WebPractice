package com.store.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    @Pattern(regexp = "^[A-Za-z]*", message = "Name should contains only characters")
    private String name;

    @NotNull
    @Pattern(regexp = "^[0-9]*", message = "Name should contains only digits")
    private Integer price;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]*", message = "Description should contains only characters")
    private String desc;
}
