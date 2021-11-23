package com.store.repositories.db_config.database;

import java.sql.Connection;

public interface DataSources {
    Connection getConnection();
}
