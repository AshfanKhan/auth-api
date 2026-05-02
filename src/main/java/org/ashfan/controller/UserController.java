package org.ashfan.controller;

import org.ashfan.entity.UserEntity;
import org.ashfan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ArrayList<UserEntity> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @DeleteMapping
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "All users deleted successfully";
    }
}
