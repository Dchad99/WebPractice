package com.store.repositories.queries;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class SqlQueryGeneratorTest {

    @Test
    void findAll() {
        SqlQueryGenerator sqlQueryGenerator = new SqlQueryGenerator();
        String expected = "SELECT * FROM products;";
        String actual = sqlQueryGenerator.findAll(TestEntity.class);
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        SqlQueryGenerator sqlQueryGenerator = new SqlQueryGenerator();
        TestEntity testEntity = new TestEntity(1, "david", 1, new Date());
        String expected = "SELECT * FROM products WHERE id=1;";
        String actual = sqlQueryGenerator.findById(testEntity.getId(), TestEntity.class);

        assertEquals(expected, actual);
    }

    @Test
    void insert() {
        SqlQueryGenerator sqlQueryGenerator = new SqlQueryGenerator();
        String expected = "INSERT INTO products (date, price, name) VALUES ('Thu Nov 18 01:03:14 EET 2021', 1, 'David');";

        TestEntity testEntity = new TestEntity(1, "David", 1, new Date());
        String actual = sqlQueryGenerator.insert(testEntity);
        System.out.println("Actual => " + actual);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        SqlQueryGenerator sqlQueryGenerator = new SqlQueryGenerator();
        String expected = "DELETE FROM products WHERE id=1;";

        TestEntity person = new TestEntity(1, "David",12000, new Date());
        String actual = sqlQueryGenerator.remove(person.getId(), TestEntity.class);

        assertEquals(expected, actual);
    }

    @SneakyThrows
    @Test
    void update(){
        SqlQueryGenerator sqlQueryGenerator = new SqlQueryGenerator();
        String expected = "UPDATE products SET name='David', price=1, date='' WHERE id=1;";

        TestEntity updated = new TestEntity(1, "David", 1, new Date());
        String actual = sqlQueryGenerator.update(updated);
        System.out.println(actual);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

}