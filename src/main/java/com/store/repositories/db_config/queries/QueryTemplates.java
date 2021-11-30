package com.store.repositories.db_config.queries;

import java.util.function.Supplier;

public enum QueryTemplates  implements Supplier<String> {
    INSERT("INSERT INTO %s (%s) VALUES (%s);"),
    SELECT_ALL("SELECT * FROM %s;"),
    SELECT_BY_PARAMETER("SELECT * FROM %s WHERE %s=%s;"),
    DELETE_BY_ID ("DELETE FROM %s WHERE %s=%s;"),
    UPDATE_BY_ID("UPDATE %s SET %s WHERE %s=%s;"),
    FIND_BY_ITEM("SELECT * FROM products WHERE name LIKE %s or description LIKE %s;"),
    FIND_BY_USER_ID("SELECT * FROM %s WHERE clientId=%s;"),
    DELETE_ORDER_BY_PRODUCT_ID("DELETE FROM %s WHERE productId=%s");

    private final String query;

    QueryTemplates(String query) {
        this.query = query;
    }

    @Override
    public String get() {
        return query;
    }
}
