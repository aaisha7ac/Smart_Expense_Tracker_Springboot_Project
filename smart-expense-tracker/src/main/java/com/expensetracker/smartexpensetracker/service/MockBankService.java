package com.expensetracker.smartexpensetracker.service;

import com.expensetracker.smartexpensetracker.model.Transaction;
import com.expensetracker.smartexpensetracker.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MockBankService {

    private static final String[] MOCK_DESCRIPTIONS = {
        "Starbucks Coffee", "Amazon Purchase", "Gas Station Fuel", "Grocery Store Visit", "Netflix Subscription"
    };
    private final Random random = new Random();

    // Generated 10 random transactions for a given user
    public List<Transaction> generateRandomTransactions(User user) {
        List<Transaction> transactions = new ArrayList<>();
        LocalDate now = LocalDate.now();

        for(int i = 0; i < 10; i++) {
            // Random Amount between 5.00 and 150.00
            double amountDouble = 5.00 + (150.00 - 5.00) * random.nextDouble();
            BigDecimal amount = new BigDecimal(String.format("%.2f",amountDouble));

            // Random Description
            String description = MOCK_DESCRIPTIONS[random.nextInt(MOCK_DESCRIPTIONS.length)];

            // Random Date (last 30 days)
            LocalDate date = now.minusDays(random.nextInt(30));

            Transaction transaction = new Transaction(amount,description,date,user);
            transactions.add(transaction);

        }

        return transactions;
    }
}
