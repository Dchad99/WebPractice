package com.store.repositories;

import com.store.entities.User;
import com.store.repositories.db_config.jdbc.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> getByParam(String parameter);
}
