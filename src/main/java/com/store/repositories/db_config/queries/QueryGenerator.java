package com.store.repositories.db_config.queries;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object object, Class<?> clazz);

    String findByParameter(Object object, Class<?> clazz);

    String insert(Object object);

    String remove(Object object, Class<?> clazz);

    String update(Object object);

    String findByItem(Object object, String searchItem);

    String findAllById(Class<?> object, int id);

    String deleteByProductId(Class<?> object, int id);
}
