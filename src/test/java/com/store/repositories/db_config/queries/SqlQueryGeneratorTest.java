package com.store.repositories.db_config.queries;

import com.store.entities.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SqlQueryGeneratorTest {
    SqlQueryGenerator sqlQueryGenerator = mock(SqlQueryGenerator.class);

    @Test
    void findAll() {
        String expected = "SELECT * FROM products;";

        when(sqlQueryGenerator.findAll(TestEntity.class)).thenReturn(expected);
        String actual = sqlQueryGenerator.findAll(TestEntity.class);

        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        TestEntity testEntity = new TestEntity(1, "david", 1);
        String expected = "SELECT * FROM products WHERE id=1;";

        when(sqlQueryGenerator.findById(testEntity.getId(), TestEntity.class)).thenReturn(expected);
        String actual = sqlQueryGenerator.findById(testEntity.getId(), TestEntity.class);

        assertEquals(expected, actual);
    }

    @Test
    void insert() {
        String expected = "INSERT INTO products (price, name) VALUES (1, 'David');";
        TestEntity testEntity = new TestEntity(1, "David", 1);

        when(sqlQueryGenerator.insert(testEntity)).thenReturn(expected);
        String actual = sqlQueryGenerator.insert(testEntity);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        String expected = "DELETE FROM products WHERE id=1;";
        TestEntity person = new TestEntity(1, "David", 12000);

        when(sqlQueryGenerator.remove(person.getId(), TestEntity.class)).thenReturn(expected);
        String actual = sqlQueryGenerator.remove(person.getId(), TestEntity.class);

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void update() {
        String expected = "UPDATE products SET price=1, name='David' WHERE id=1;";
        TestEntity updated = new TestEntity(1, "David", 1);

        when(sqlQueryGenerator.update(updated)).thenReturn(expected);
        String actual = sqlQueryGenerator.update(updated);

        assertEquals(expected, actual);
    }


    @Test
    void testSearchByProductNameAndDescription() {
        String expected = "SELECT * FROM products WHERE name LIKE '%s%' or description LIKE '%s%';";
        Product product = new Product(1, "product", 1, new Date(), "description");

        when(sqlQueryGenerator.findByItem(product, "s")).thenReturn(expected);
        String actual = sqlQueryGenerator.findByItem(product, "s");

        assertEquals(expected, actual);
    }


}