package com.store.services;

import com.store.entities.Product;
import com.store.repositories.ProductRepository;
import com.store.repositories.impl.ProductRepositoryImpl;
import com.store.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    private final ProductService service = mock(ProductServiceImpl.class);
    private final ProductRepository repository = mock(ProductRepositoryImpl.class);
    private Product product = mock(Product.class);

    @BeforeEach
    void setUp() {
        product = new Product(1, "David", 1, new Date(), "");
    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    void testCreate() {
        when(repository.save(product)).thenReturn(true);
        service.save(product);
        verify(service, times(1)).save(any());
    }

    @Test
    void givenIdThenShouldReturnDomainOfThatId() {
        when(service.getById(product.getId())).thenReturn(Optional.ofNullable(product));
        assertNotNull(service.getById(product.getId()));
        verify(service, times(1)).getById(product.getId());
        assertEquals(service.getById(product.getId()), Optional.of(product));
    }

    @Test
    void deleteTest() {
        var absentShort = 123;
        var presentShort = 1;

        when(service.getById(absentShort)).thenReturn(Optional.empty());
        when(service.getById(presentShort)).thenReturn(Optional.of(product));

        service.delete(product);
        verify(service, times(1)).delete(product);
        verifyNoMoreInteractions(service);
    }

}