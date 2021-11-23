package com.store.services.impl;

import com.store.entities.User;
import com.store.repositories.UserRepository;
import com.store.repositories.impl.UserRepositoryImpl;
import com.store.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private final UserService service = mock(UserServiceImpl.class);
    private final UserRepository repository = mock(UserRepositoryImpl.class);
    private User user = mock(User.class);

    @BeforeEach
    void setUp() {
        user = new User(2, "David", "q1w2e3r4", "qwer");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void testCreate() {
        when(repository.save(user)).thenReturn(true);
        service.save(user);
        verify(service, times(1)).save(any());
    }

    @Test
    void givenIdThenShouldReturnDomainOfThatId() {
        when(service.getById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertNotNull(service.getById(user.getId()));
        verify(service, times(1)).getById(user.getId());
        assertEquals(service.getById(user.getId()), Optional.of(user));
    }

    @Test
    void deleteTest() {
        var absentShort = 123;
        var presentShort = 2;

        when(service.getById(absentShort)).thenReturn(Optional.empty());
        when(service.getById(presentShort)).thenReturn(Optional.of(user));

        service.delete(user);
        verify(service, times(1)).delete(user);
        verifyNoMoreInteractions(service);
    }
}