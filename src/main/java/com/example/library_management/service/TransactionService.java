package com.example.library_management.service;

import com.example.library_management.model.Transaction;
import com.example.library_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Get a transaction by ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with ID: " + id));
    }

    // Save a new transaction
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Delete a transaction by ID
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }

    // Fetch transactions by user ID and action
    public List<Transaction> getTransactionsByUserIdAndAction(Long userId, String action) {
        return transactionRepository.findAllByUserIdAndAction(userId, action);
    }
}
