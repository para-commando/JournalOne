package com.JournalOne.JournalOne.service;

import com.JournalOne.JournalOne.entity.User;
import com.JournalOne.JournalOne.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepo userRepo;

    @Test
    void loadUserByUserNameTest() {
        User user = new User();
        user.setUserName("user123");
        user.setPassword("password123");
        user.setRoles(Arrays.asList("ADMIN"));

        // Mocking the UserRepo behavior
        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));

        // Testing the loadUserByUsername method
        UserDetails userDetails = userDetailsService.loadUserByUsername("user123");

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("user123", userDetails.getUsername());
        Assertions.assertEquals("password123", userDetails.getPassword());
    }

    @Test
    void loadUserByUserNameNotFoundTest() {
        // Mocking the UserRepo behavior for user not found
        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        // Testing the loadUserByUsername method and expecting an exception
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("user123");
        });
    }
}
