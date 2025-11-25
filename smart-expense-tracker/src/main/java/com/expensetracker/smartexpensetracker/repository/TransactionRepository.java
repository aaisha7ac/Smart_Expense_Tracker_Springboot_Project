package com.expensetracker.smartexpensetracker.repository;

import com.expensetracker.smartexpensetracker.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByUserId(Long userId);
    @Transactional
    void deleteByUserId(Long userId);
}
