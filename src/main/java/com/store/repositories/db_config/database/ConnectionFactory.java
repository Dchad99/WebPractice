package com.store.repositories.db_config.database;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionFactory implements DataSources {

    @SneakyThrows
    @Override
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test_db", "root", "root");
            log.info("Successfully connected to DB!");
            return connection;
        } catch (SQLException e) {
            log.warn("Connection to database was failed.", e);
            throw new RuntimeException("Connection to database was failed.", e);
        }
    }


}
