package com.expensetracker.smartexpensetracker.service;

import com.expensetracker.smartexpensetracker.model.User;
import com.expensetracker.smartexpensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveNewUser(User user) {
        // In Phase 2, you would add logic here to check if the username exists
        // and securely hash the password before saving.
        return userRepository.save(user);
    }
}