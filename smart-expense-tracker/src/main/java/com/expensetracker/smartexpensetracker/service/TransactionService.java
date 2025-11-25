package com.expensetracker.smartexpensetracker.service;

import com.expensetracker.smartexpensetracker.model.Transaction;
import com.expensetracker.smartexpensetracker.model.User;
import com.expensetracker.smartexpensetracker.repository.TransactionRepository;
import com.expensetracker.smartexpensetracker.repository.UserRepository;
import com.expensetracker.smartexpensetracker.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring business service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final MockBankService mockBankService;// Injecting the mock service

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, MockBankService mockBankService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.mockBankService = mockBankService;
    }

    // --- CRUD Operations ---

    // GET: Find all transactions for a specific user
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // GET: Find a single transaction by ID and user ID
    public Optional<Transaction> getTransactionById(Long id, Long userId) {
        return transactionRepository.findById(id)
                .filter(t->t.getUser().getId().equals(userId));
    }

    // POST: Create a new transaction
    public Transaction createTransaction(Long userId, Transaction transaction) {

        // Find the user object to link the transaction
        User user = userRepository.findById(userId)
//                .orElseThrow(()-> new RuntimeException("User not found with ID: " + userId));
        // Throw UserNotFoundException instead of RuntimeException
                .orElseThrow(()->new UserNotFoundException("UserId: " + userId + " doesn't exists"));

        transaction.setUser(user);

        // *Phase 2 Note: Automated categorization logic will go here (Workflow2, Step 4)*
        return transactionRepository.save(transaction);
    }

    // PUT: Update an existing transaction
    public Optional<Transaction> updateTransaction(Long id, Long userId, Transaction updatedDetails) {
        // 1.Check if the transaction exists AND belongs to the user
        return getTransactionById(id, userId).map(existingTransaction -> {
            // 2. Update fields
            existingTransaction.setAmount(updatedDetails.getAmount());
            existingTransaction.setDescription(updatedDetails.getDescription());
            existingTransaction.setDate(updatedDetails.getDate());
            existingTransaction.setCategory(updatedDetails.getCategory());

            // 3. Save and return the updated entity
            return transactionRepository.save(existingTransaction);
        });
    }

    // DELETE:
    // 1. Delete Single transaction
    public boolean deleteSingleTransaction(Long id, Long userId) {
        if(getTransactionById(id, userId).isPresent()) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // 2. Delete ALl Transactions for a specific user
    public void deleteAllTransactionsForUser(Long userID) {

        // This calls the custom method we will define in the repository
        transactionRepository.deleteByUserId(userID);

    }

    // --- Mock Bank API Feature ---

    // POST: Generate mock transactions for a user
    public List<Transaction> generateMockTransactions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("UserId: " + userId + " doesn't exists"));

        List<Transaction> mockTransactions = mockBankService.generateRandomTransactions(user);
        // Save the generated transactions to the database
        return transactionRepository.saveAll(mockTransactions);
    }
}


//package com.expensetracker.smartexpensetracker.service;
//
//import com.expensetracker.smartexpensetracker.model.Transaction;
//import com.expensetracker.smartexpensetracker.model.User;
//import com.expensetracker.smartexpensetracker.repository.TransactionRepository;
//import com.expensetracker.smartexpensetracker.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service // Marks this class as a Spring business service
//public class TransactionService {
//
//    private final TransactionRepository transactionRepository;
//    private final UserRepository userRepository;
//    private final MockBankService mockBankService; // Injecting the mock service
//
//    @Autowired // Dependency Injection: Spring automatically provides the required instances
//    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, MockBankService mockBankService) {
//        this.transactionRepository = transactionRepository;
//        this.userRepository = userRepository;
//        this.mockBankService = mockBankService;
//    }
//
//    // --- CRUD Operations ---
//
//    // GET: Find all transactions for a specific user
//    public List<Transaction> getTransactionsByUserId(Long userId) {
//        return transactionRepository.findByUserId(userId);
//    }
//
//    // GET: Find a single transaction by ID and user ID
//    public Optional<Transaction> getTransactionById(Long id, Long userId) {
//        return transactionRepository.findById(id)
//                .filter(t -> t.getUser().getId().equals(userId));
//    }
//
//    // POST: Create a new transaction
//    public Transaction createTransaction(Long userId, Transaction transaction) {
//        // Find the user object to link the transaction
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        transaction.setUser(user);
//        // *Phase 2 Note: Automated categorization logic will go here (Workflow 2, Step 4)*
//        return transactionRepository.save(transaction);
//    }
//
//    // PUT: Update an existing transaction
//    public Optional<Transaction> updateTransaction(Long id, Long userId, Transaction updatedDetails) {
//        // 1. Check if the transaction exists AND belongs to the user
//        return getTransactionById(id, userId).map(existingTransaction -> {
//            // 2. Update fields
//            existingTransaction.setAmount(updatedDetails.getAmount());
//            existingTransaction.setDescription(updatedDetails.getDescription());
//            existingTransaction.setDate(updatedDetails.getDate());
//            existingTransaction.setCategory(updatedDetails.getCategory());
//
//            // 3. Save and return the updated entity
//            return transactionRepository.save(existingTransaction);
//        });
//    }
//
//    // DELETE: Delete a transaction
//    public boolean deleteTransaction(Long id, Long userId) {
//        if (getTransactionById(id, userId).isPresent()) {
//            transactionRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//
//    // --- Mock Bank API Feature ---
//
//    // POST: Generate mock transactions for a user
//    public List<Transaction> generateMockTransactions(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        List<Transaction> mockTransactions = mockBankService.generateRandomTransactions(user);
//        // Save the generated transactions to the database
//        return transactionRepository.saveAll(mockTransactions);
//    }
//}
