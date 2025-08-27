package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody String updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        // Find the existing user
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update the user's name and email with the new values
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            // Save the updated user
            userRepository.save(existingUser);
            return "Updated";
        } else {
            return "User not found";
        }
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteUser(@PathVariable Integer id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "Deleted";
        }
        else {
            return "User not found";
        }
    }
}