package com.example.library_management.controller;

import com.example.library_management.dto.BookDTO;
import com.example.library_management.model.Book;
import com.example.library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    // Method to retrieve all books
    @GetMapping("/allBooks")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getAvailableCopies()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<BookDTO> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Long id) {

        // Perform search logic based on provided filters
        return bookService.searchBooks(title, author, id).stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getAvailableCopies()
                ))
                .collect(Collectors.toList());
    }


    @GetMapping("/borrowed")
    public List<BookDTO> getBorrowedBooks() {
        return bookService.getBorrowedBooks().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getAvailableCopies()
                ))
                .collect(Collectors.toList());
    }

}
