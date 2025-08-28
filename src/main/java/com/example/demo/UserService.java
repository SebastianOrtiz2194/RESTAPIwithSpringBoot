package com.example.demo;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserById(Integer id);
    List<User> findAllUsers();
    User updateUser(Integer id, UserDTO updatedUserDTO);
    void deleteUser(Integer id);
}
