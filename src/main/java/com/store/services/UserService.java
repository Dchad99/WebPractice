package com.store.services;

import com.store.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    boolean delete(User user);

    Optional<User> getById(int id);

    boolean save(User user);

    boolean update(User object);

    Optional<User> getRecordByParam(String parameter);
}
