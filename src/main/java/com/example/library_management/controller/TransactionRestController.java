package com.example.library_management.controller;

import com.example.library_management.dto.TransactionDTO;
import com.example.library_management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getUser().getUsername(),
                        transaction.getBook().getTitle(),
                        transaction.getAction(),
                        transaction.getDate()
                ))
                .collect(Collectors.toList());
    }
}
