package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/api/users")
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User addNewUser (@Valid @RequestBody UserDTO newUserDTO) {
        // Create a new User entity from the DTO
        User newUser = new User();
        newUser.setName(newUserDTO.getName());
        newUser.setEmail(newUserDTO.getEmail());

        return userService.saveUser(newUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path="/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping(path="/{id}")
    public User updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO updatedUserDTO) {
        return userService.updateUser(id, updatedUserDTO);
    }

    @DeleteMapping(path="/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "Deleted user with id " + id;
    }
}