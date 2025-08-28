package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// We use MockitoExtension to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    // We use @Mock to create a mock about the UserRepository
    @Mock
    private UserRepository userRepository;

    // We use @InjectMocks to inject the mock into the UserService implementation
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        // When we call userRepository.save, we want it to return the user
        when(userRepository.save(user)).thenReturn(user);

        // When
        User savedUser = userService.saveUser(user);

        // Then
        assertNotNull(savedUser);
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
    }

    @Test
    void testFindUserById_WhenUserExists() {
        // Given
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");

        // When we call findById with ID 1, we want it to return an Optional with the user
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userService.findUserById(1);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(1, foundUser.get().getId());
    }

    @Test
    void testFindUserById_WhenUserDoesNotExist() {
        // Given
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = userService.findUserById(99);

        // Then
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindAllUsers() {
        // Given
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        // When we call findAll, we want it to return the list of users
        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> allUsers = userService.findAllUsers();

        // Then
        assertFalse(allUsers.isEmpty());
        assertEquals(2, allUsers.size());
    }

    @Test
    void testDeleteUser_WhenUserExists() {
        // Given
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        // When
        userService.deleteUser(1);

        // Then
        // Verify that the deleteById method was called exactly once
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUser_WhenUserDoesNotExist() {
        // Given
        when(userRepository.existsById(99)).thenReturn(false);

        // Then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99));

        // Verify that the deleteById method was never called
        verify(userRepository, never()).deleteById(99);
    }
}