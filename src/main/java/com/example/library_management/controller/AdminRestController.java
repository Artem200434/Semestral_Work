package com.example.library_management.controller;

import com.example.library_management.service.BookService;
import com.example.library_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/totalUsers")
    public Map<String, Long> getTotalUsers() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalUsers", (long) userService.getAllUsers().size());
        return response;
    }

    @GetMapping("/totalBooks")
    public Map<String, Long> getTotalBooks() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalBooks", (long) bookService.getAllBooks().size());
        return response;
    }

    @GetMapping("/borrowedBooks")
    public Map<String, Long> getBorrowedBooks() {
        Map<String, Long> response = new HashMap<>();
        response.put("borrowedBooks", bookService.getAllBooks().stream()
                .filter(book -> book.getAvailableCopies() == 0)
                .count());
        return response;
    }


}
