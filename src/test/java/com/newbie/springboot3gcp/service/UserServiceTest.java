package com.newbie.springboot3gcp.service;

import com.newbie.springboot3gcp.entity.User;
import com.newbie.springboot3gcp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        // GIVEN
        var user = new User();
        user.setId("123456789");
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setCountry("TS");

        // WHEN
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(Mono.just(user));
        StepVerifier.create(userServiceImpl.save(user))
                .expectComplete();
    }
}
