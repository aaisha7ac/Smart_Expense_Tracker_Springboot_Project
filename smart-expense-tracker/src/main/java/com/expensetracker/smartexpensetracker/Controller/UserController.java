package com.expensetracker.smartexpensetracker.Controller;

import com.expensetracker.smartexpensetracker.model.User;
import com.expensetracker.smartexpensetracker.service.UserService; // You'll need this Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // 1. Marks this as a controller
@RequestMapping("/api/users") // 2. Sets the base path for user operations

public class UserController {

    private final UserService userService; // Dependency Injection

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 3. The ACTUAL Endpoint for creating a user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created on success
    public User registerUser(@RequestBody User user) {
        // This line calls your service layer to save the new User object
        return userService.saveNewUser(user);
    }
}
