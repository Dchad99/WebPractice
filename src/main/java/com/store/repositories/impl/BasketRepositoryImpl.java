package com.store.repositories.impl;

import com.store.entities.Order;
import com.store.repositories.BasketRepository;
import com.store.repositories.db_config.database.DataSources;
import com.store.repositories.db_config.queries.QueryGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class BasketRepositoryImpl implements BasketRepository {
    private final DataSources connectionFactory;
    private final QueryGenerator queryGenerator;

    @Override
    public List<Order> getAllOrdersByUserId(int id) {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findAllById(Order.class, id))) {

            System.out.println("Query => " + queryGenerator.findAllById(Order.class, id));

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setClientId(resultSet.getInt("clientId"));
                order.setProductId(resultSet.getInt("productId"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAllById', query is incorrect: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean deleteByProductId(int id) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            statement.executeUpdate(queryGenerator.deleteByProductId(Order.class, id));
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'delete', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public List<Order> getAll() {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findAll(Order.class))) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setClientId(resultSet.getInt("clientId"));
                order.setProductId(resultSet.getInt("productId"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(Order order) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            statement.executeUpdate(queryGenerator.remove(order.getId(), Order.class));
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'delete', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public Optional<Order> getById(int id) {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findById(id, Order.class))) {
            Order order = new Order();
            while (resultSet.next()) {
                order.setId(resultSet.getInt("id"));
                order.setClientId(resultSet.getInt("clientId"));
                order.setProductId(resultSet.getInt("productId"));
            }
            return Optional.of(order);
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Order order) {
        boolean status = false;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            String query = queryGenerator.insert(order);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'save', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public boolean update(Order order) {
        boolean status = false;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            String query = queryGenerator.update(order);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'update', query is incorrect: ", e);
        }
        return status;
    }
}
