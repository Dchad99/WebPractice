package com.store.repositories.db_config.database;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {
    private DataSources dataSource = new ConnectionFactory();
    private Connection connection;
    private Statement statement;

    @Test
    void checkIfConnectionIsNotNull(){
        connection = dataSource.getConnection();
        assertNotNull(connection);
    }

    @SneakyThrows
    @Test
    void checkIfStatementIsNotNull(){
        connection = dataSource.getConnection();
        assertNotNull(connection);

        statement = connection.createStatement();
        assertNotNull(statement);
    }

}