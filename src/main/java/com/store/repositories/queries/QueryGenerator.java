package com.store.repositories.queries;

public interface QueryGenerator {
    String findAll(Class<?> clazz);

    String findById(Object object, Class<?> clazz);

    String insert(Object object);

    String remove(Object object, Class<?> clazz);

    String update(Object object);
}
