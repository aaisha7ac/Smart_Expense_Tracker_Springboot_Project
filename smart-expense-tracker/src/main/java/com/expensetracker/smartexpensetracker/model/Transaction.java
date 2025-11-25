package com.expensetracker.smartexpensetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    // Default value for category during Phase 1 testing
    private String category = "Uncategorized";

    @Column(nullable = false)
    private LocalDate date;

    // Many transactions belong to one user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;

    // Constructor for initial population/testing (optional)
    public Transaction(BigDecimal amount, String description, LocalDate date, User user) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
    }
}