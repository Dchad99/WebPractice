package com.store.repositories.database;

import java.sql.Connection;

public interface DataSources {
    Connection getConnection();
}
