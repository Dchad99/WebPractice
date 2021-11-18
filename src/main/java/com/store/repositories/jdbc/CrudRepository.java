package com.store.repositories.jdbc;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> getAll();

    boolean delete(T t);

    Optional<T> getById(int id);

    boolean save(T t);

    boolean update(T t);
}
