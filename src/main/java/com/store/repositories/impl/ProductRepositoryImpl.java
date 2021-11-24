package com.store.repositories.impl;

import com.store.entities.Product;
import com.store.repositories.ProductRepository;
import com.store.repositories.db_config.database.DataSources;
import com.store.repositories.db_config.queries.QueryGenerator;
import com.store.repositories.db_config.queries.SqlQueryGenerator;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    private final DataSources connectionFactory;
    private final QueryGenerator queryGenerator;

    public ProductRepositoryImpl(DataSources connectionFactory) {
        this.connectionFactory = connectionFactory;
        queryGenerator = new SqlQueryGenerator();
    }

    @Override
    public List<Product> getAll() {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findAll(Product.class))) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDate(resultSet.getDate("date"));
                product.setPrice(resultSet.getInt("price"));
                product.setDescription(resultSet.getString("description"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(Product product) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            statement.executeUpdate(queryGenerator.remove(product.getId(), Product.class));
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'delete', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public Optional<Product> getById(int id) {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findById(id, Product.class))) {
            Product product = new Product();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDate(resultSet.getDate("date"));
                product.setPrice(resultSet.getInt("price"));
                product.setDescription(resultSet.getString("description"));
            }
            return Optional.of(product);
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Product product) {
        boolean status = false;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            String query = queryGenerator.insert(product);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'save', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public boolean update(Product product) {
        boolean status = false;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            String query = queryGenerator.update(product);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'update', query is incorrect: ", e);
        }
        return status;
    }

    public List<Product> findByNameAndDescription(String searchItem) {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findByItem(Product.class, searchItem))) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setDate(resultSet.getDate("date"));
                product.setName(resultSet.getString("description"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findByNameAndDescription', query is incorrect: ", e);
        }
        return Collections.emptyList();
    }
}
