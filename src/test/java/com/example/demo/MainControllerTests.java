package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        // Given
        User user1 = new User();
        user1.setId(1);
        user1.setName("User 1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2);
        user2.setName("User 2");
        user2.setEmail("user2@example.com");

        List<User> allUsers = Arrays.asList(user1, user2);
        when(userService.findAllUsers()).thenReturn(allUsers);

        // When/Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("User 1"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }

    @Test
    void testAddNewUser_Success() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("New User");
        userDTO.setEmail("new.user@example.com");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("New User");
        savedUser.setEmail("new.user@example.com");

        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        // When/Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New User"));
    }

    @Test
    void testAddNewUser_ValidationFailure() throws Exception {
        // Given
        UserDTO invalidUserDTO = new UserDTO();
        invalidUserDTO.setName("a"); // Too short
        invalidUserDTO.setEmail("invalid"); // Invalid email

        // When/Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 50 characters"))
                .andExpect(jsonPath("$.email").value("Email should be a valid email address"));
    }
}
