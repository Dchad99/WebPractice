package com.store.services.impl;

import com.store.entities.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.UserRepository;
import com.store.services.UserService;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(User user) {
        return repository.delete(user);
    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.of(repository.getById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public boolean save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean update(User user) {
        return repository.update(user);
    }

    @Override
    public Optional<User> getRecordByParam(String parameter) {
        return Optional.of(repository.getRecordByParam(parameter)).orElseThrow(ResourceNotFoundException::new);
    }

}
