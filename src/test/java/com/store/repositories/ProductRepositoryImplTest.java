package com.store.repositories;

import com.store.entities.Product;
import com.store.repositories.impl.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {
    private ProductRepository repository = mock(ProductRepositoryImpl.class);
    private static Product product = mock(Product.class);

    @BeforeEach
    void beforeAll() {
        product = new Product(1, "David", 1, new Date());
    }

    @Test
    void checkMethodGetAll(){
        assertNotNull(repository.getAll());
    }

    @Test
    void testDelete(){
        var absentId = 2222;
        var presentId = 1;

        var product = new Product(presentId, "David", 1, new Date());
        when(repository.getById(absentId)).thenReturn(empty());
        when(repository.getById(presentId)).thenReturn(of(product));

        when(repository.delete(product)).thenReturn(true);
    }

    @Test
    void saveMethod(){
        when(repository.save(product)).thenReturn(true);
        repository.save(product);
        verify(repository, times(1)).save(product);
    }


    @Test
    void testUpdateProduct(){
        when(repository.update(product)).thenReturn(true);
        repository.update(product);
        verify(repository, times(1)).update(product);
    }

}