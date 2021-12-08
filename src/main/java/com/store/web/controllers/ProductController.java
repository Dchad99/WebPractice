package com.store.web.controllers;
import com.store.web.dto.ProductDTO;
import com.store.entities.Product;
import com.store.exceptions.RequestException;
import com.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Validated
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping(path = "/productPage")
    public String getAllProductPage() {
        return "static/html/productTable.html";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @PostMapping("/products/update")
    public ResponseEntity<?> updateProduct(@Valid @ModelAttribute ProductDTO productDTO, @RequestParam Integer id) {
        Optional<Product> productToUpdate = service.getById(id);

        if (productToUpdate.isPresent()) {
            Product p = productToUpdate.get();

            p.setName(productDTO.getName());
            p.setPrice(productDTO.getPrice());
            p.setDate(new Date());
            p.setDescription(productDTO.getDesc());

            return new ResponseEntity<>(service.update(p), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam(required = false) String search) {
        return new ResponseEntity<>(service.findByNameAndDescription(search), HttpStatus.OK);
    }

    @PostMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        Optional<Product> product = service.getById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.delete(product.get()), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = service.getById(id);
        if (!product.isPresent()) {
            throw new RequestException("Product wasn't found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/products/add")
    public String createProduct(@Valid @ModelAttribute ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(),  productDTO.getDesc(), productDTO.getPrice());

        product.setDate(new Date());
        service.save(product);
        return "redirect:/productPage";
    }

}