package com.store.services.impl;

import com.store.entities.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.UserRepository;
import com.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(User user) {
        Optional<User> entity = repository.getById(user.getId());
        if(entity.isPresent()){
            return repository.delete(entity.get());
        }
        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User wasn't found");
    }

    @Override
    public Optional<User> getById(int id) {
        Optional<User> user = repository.getById(id);

        if(user.isPresent()){
            return user;
        }

        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User wasn't found");
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
    public Optional<User> getByParam(String parameter) {
        Optional<User> user = repository.getByParam(parameter);

        if(user.isPresent()){
            return user;
        }

        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User wasn't found");
    }

}
