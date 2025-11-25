package com.expensetracker.smartexpensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // From Lombok: generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // From Lombok: generates a no-argument constructor
@Table(name = "users") // Good practice to use plural for table names
public class User {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password; // In Phase 2, this will be securely hashed
}