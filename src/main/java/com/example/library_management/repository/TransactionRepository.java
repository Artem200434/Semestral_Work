package com.example.library_management.repository;

import com.example.library_management.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserIdAndAction(Long userId, String action);

    List<Transaction> findAllByAction(String action);
}
