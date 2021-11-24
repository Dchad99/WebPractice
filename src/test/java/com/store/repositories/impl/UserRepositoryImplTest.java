package com.store.repositories.impl;

import com.store.entities.User;
import com.store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserRepositoryImplTest {
    private UserRepository repository = mock(UserRepositoryImpl.class);
    private static User user = mock(User.class);

    @BeforeEach
    void beforeAll() {
        user = new User(1, "David", "qwer1234", "dff22dsfs");
    }

    @Test
    void checkMethodGetAll(){
        assertNotNull(repository.getAll());
    }


    @Test
    void testDeleteUser(){
        var absentId = 2222;
        var presentId = 2;

        var product = new User(presentId, "David", "q1w2e3r4", "qwer");
        when(repository.getById(absentId)).thenReturn(empty());
        when(repository.getById(presentId)).thenReturn(of(product));

        when(repository.delete(product)).thenReturn(true);
    }

    @Test
    void saveMethod(){
        when(repository.save(user)).thenReturn(true);
        repository.save(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    void testUpdateProduct(){
        when(repository.update(user)).thenReturn(true);
        repository.update(user);
        verify(repository, times(1)).update(user);
    }

}