package com.store.repositories.impl;

import com.store.entities.User;
import com.store.repositories.UserRepository;
import com.store.repositories.db_config.database.DataSources;
import com.store.repositories.db_config.queries.QueryGenerator;
import com.store.repositories.db_config.queries.SqlQueryGenerator;
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
public class UserRepositoryImpl implements UserRepository {
    private final DataSources connectionFactory;
    private final QueryGenerator queryGenerator;

    @Override
    public List<User> getAll() {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findAll(User.class))) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User product = new User();
                product.setId(resultSet.getInt("id"));
                product.setUsername(resultSet.getString("username"));
                product.setPassword(resultSet.getString("password"));
                product.setUserHash(resultSet.getString("userHash"));
                users.add(product);
            }
            return users;
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(User user) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            statement.executeUpdate(queryGenerator.remove(user.getId(), User.class));
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'delete', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public Optional<User> getById(int id) {
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(queryGenerator.findByParameter(id, User.class))) {
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserHash(resultSet.getString("userHash"));
            }

            return Optional.of(user);
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(User user) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement();) {
            String query = queryGenerator.insert(user);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'save', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public boolean update(User user) {
        boolean status;
        try (var connection = connectionFactory.getConnection();
             var statement = connection.createStatement()) {
            String query = queryGenerator.update(user);
            statement.executeUpdate(query);
            status = true;
        } catch (SQLException e) {
            status = false;
            log.warn("Couldn't invoke 'update', query is incorrect: ", e);
        }
        return status;
    }

    @Override
    public Optional<User> getByParam(String parameter) {
        try (var c = connectionFactory.getConnection();
             var statement = c.createStatement();
             var resultSet = statement.executeQuery(
                     queryGenerator.findByParameter(parameter, User.class))) {

            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setUserHash(resultSet.getString("userHash"));
            }

            return Optional.of(user);
        } catch (SQLException e) {
            log.warn("Couldn't invoke 'findAll', query is incorrect: ", e);
        }
        return Optional.empty();
    }
}
