//package com.expensetracker.smartexpensetracker.Controller;
//
//import com.expensetracker.smartexpensetracker.model.Transaction;
//import com.expensetracker.smartexpensetracker.service.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController // Marks this class as a REST API Controller
//@RequestMapping("/api/transactions") // Base path for all methods in this compiler
//
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    @Autowired
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    // --- Mock User/Authentication Setup for Phase 1 ---
//    // In phase 1, we will hardcode the userId = 1 for testing,
//    // as Spring Security (Workflow 1) is not implemented yet.
//    private final Long MOCK_USER_ID = 1L;
//
//    // --- CRUD Endpoints ---
//
//    // GET /api/transactions
//    @GetMapping
//    public List<Transaction> getAllTransactionsForUser() {
//      return transactionService.getTransactionsByUserId(MOCK_USER_ID);
//    }
//
//    // GET /api/transactions/{id}
//    @GetMapping("/{id}")
//    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
//        return transactionService.getTransactionById(id,MOCK_USER_ID)
//                .map(ResponseEntity::ok) // If found, return 200 OK with the transaction
//                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found
//
//    }
//
//    //  ADDED: POST /api/transactions (CREATE)
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
//    public Transaction createTransaction(@RequestBody Transaction transaction) {
//        return transactionService.createTransaction(MOCK_USER_ID, transaction);
//    }
//
//    //  ADDED: PUT /api/transactions/{id} (UPDATE)
//    @PutMapping("/{id}")
//    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
//        return transactionService.updateTransaction(id, MOCK_USER_ID, transactionDetails)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // DELETE /api/transactions/{id}
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTransactions(@PathVariable Long id) {
//        boolean deleted = transactionService.deleteTransaction(id, MOCK_USER_ID);
//
//        if(deleted) {
//            return ResponseEntity.noContent().build(); // HTTP 204 No Content
//        }
//
//        return ResponseEntity.notFound().build(); // HTTP 404 Not Found
//    }
//
//    // --- MOCK Bank API Endpoint ---
//
//    // POST /api/transactions/mock
//    @PostMapping("/mock")
//    @ResponseStatus(HttpStatus.CREATED)
//
//    public List<Transaction> generateMockTransactions() {
//        return transactionService.generateMockTransactions(MOCK_USER_ID);
//    }
//}


package com.expensetracker.smartexpensetracker.Controller;

import com.expensetracker.smartexpensetracker.model.Transaction;
import com.expensetracker.smartexpensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST API Controller
@RequestMapping("/api/users/{userId}/transactions") // Base path for all methods in this compiler

public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    // --- Mock User/Authentication Setup for Phase 1 ---
//    // In phase 1, we will hardcode the userId = 1 for testing,
//    // as Spring Security (Workflow 1) is not implemented yet.
//    private final Long MOCK_USER_ID = 1L;

    // --- CRUD Endpoints ---

    // GET /api/users/{userId}/transactions
    @GetMapping
    public List<Transaction> getAllTransactionsForUser(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    // GET /api/users/{userId}/transactions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id,@PathVariable Long userId) {
        return transactionService.getTransactionById(id,userId)
                .map(ResponseEntity::ok) // If found, return 200 OK with the transaction
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found

    }

    //  ADDED: POST /api/users/{userId}/transactions (CREATE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public Transaction createTransaction(@PathVariable Long userId,@RequestBody Transaction transaction) {
        return transactionService.createTransaction(userId, transaction);
    }

    //  ADDED: PUT /api/users/{userId}/transactions/{id} (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,@PathVariable Long userId, @RequestBody Transaction transactionDetails) {
        return transactionService.updateTransaction(id,userId, transactionDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/users/{userId}/transactions/{id}
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deleteSingleTransaction(
            @PathVariable Long userId, // user id
            @PathVariable Long id // transaction id
    ) {
        // The service method checks if transaction 'id' belong to 'userId' before deleting.
        boolean check = transactionService.deleteSingleTransaction(id,userId);

        if(check) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.notFound().build(); // HTTP 404
    }

    // DELETE //api/users/{userId}/transacitions
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactionsForUser(@PathVariable Long userId) {

        // Call a new service method to delete ALL transaction linked to this user ID
        transactionService.deleteAllTransactionsForUser(userId);

        // It's generally a successful action even if zero records were deleted.
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // --- MOCK Bank API Endpoint ---

    // POST /api/transactions/mock
    @PostMapping("/mock")
    @ResponseStatus(HttpStatus.CREATED)

    public List<Transaction> generateMockTransactions(@PathVariable Long userId) {
        return transactionService.generateMockTransactions(userId);
    }
}


