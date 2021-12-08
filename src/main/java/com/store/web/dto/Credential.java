package com.store.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class Credential {


    @NotNull
    @Pattern(regexp = "^[A-Za-z]*", message = "Name should contains only characters")
    private String username;

    @NotNull
    private String password;
}
