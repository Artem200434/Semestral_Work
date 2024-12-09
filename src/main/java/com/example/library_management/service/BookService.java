package com.example.library_management.service;

import com.example.library_management.model.Book;
import com.example.library_management.model.Transaction;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            // Update existing book
            Optional<Book> existingBook = bookRepository.findById(book.getId());
            if (existingBook.isPresent()) {
                Book updatedBook = existingBook.get();
                updatedBook.setTitle(book.getTitle());
                updatedBook.setAuthor(book.getAuthor());
                updatedBook.setIsbn(book.getIsbn());
                updatedBook.setAvailableCopies(book.getAvailableCopies());
                return bookRepository.save(updatedBook);
            }
        }
        // If no ID or book does not exist, create new book
        return bookRepository.save(book);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Save a new book
    public Book saveNewBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty for new books");
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty for new books");
        }

        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists. Use the update functionality instead.");
        }

        return bookRepository.save(book);
    }

    // Fetch books borrowed by user ID
    public List<Book> getBorrowedBooksByUserId(Long userId) {
        return transactionRepository.findAllByUserIdAndAction(userId, "borrowed")
                .stream()
                .map(Transaction::getBook)
                .toList();
    }

    // Fetch all borrowed books
    public List<Book> getBorrowedBooks() {
        List<Transaction> borrowedTransactions = transactionRepository.findAllByAction("Borrowed");

        // Ensure that transactions and associated books are fetched correctly
        return borrowedTransactions.stream()
                .map(Transaction::getBook) // Ensure this works
                .filter(book -> book != null) // Handle any null references
                .collect(Collectors.toList());
    }



    public List<Book> searchBooks(String title, String author, Long id) {
        // Handle search filters
        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (id != null) {
            return bookRepository.findById(id).stream().toList();
        }
        return List.of();
    }





}
