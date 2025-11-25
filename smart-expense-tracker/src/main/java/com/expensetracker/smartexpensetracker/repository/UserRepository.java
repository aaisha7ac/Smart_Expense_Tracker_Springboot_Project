package com.expensetracker.smartexpensetracker.repository;

import com.expensetracker.smartexpensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
// JpaRepository provides basic CRUD methods (save, findById, findAll, etc.)
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method to find a user by their username (needed for login/validation)
    Optional<User> findByUsername(String username);
}