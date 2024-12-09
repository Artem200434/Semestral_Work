package com.example.library_management.controller;

import com.example.library_management.model.Transaction;
import com.example.library_management.model.User;
import com.example.library_management.model.Book;
import com.example.library_management.service.TransactionService;
import com.example.library_management.service.UserService;
import com.example.library_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String redirectToAdmin() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        // Статистика
        long totalUsers = userService.getAllUsers().size();
        long totalBooks = bookService.getAllBooks().size();
        long booksLentOut = bookService.getAllBooks().stream()
                .filter(book -> book.getAvailableCopies() == 0)
                .count();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("booksLentOut", booksLentOut);

        // Данные для таблиц
        List<User> users = userService.getAllUsers();
        List<Book> books = bookService.getAllBooks();
        List<Transaction> transactions = transactionService.getAllTransactions();

        model.addAttribute("users", users);
        model.addAttribute("books", books);
        model.addAttribute("transactions", transactions); // Include transactions

        return "admin-dashboard";
    }


    @PostMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editUser/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            System.out.println("Editing user: " + user.getUsername());
            return "edit-user-form"; // Thymeleaf template for editing the user
        }
        System.err.println("User not found with ID: " + id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "edit-book-form";  // Thymeleaf template for editing the book
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/updateBook")
    public String updateBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/admin";  // Redirect back to the admin dashboard after updating the book
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(User user, Model model) {
        userService.saveUser(user);
        return "redirect:/admin"; // Redirect to the dashboard after successful update
    }


    @GetMapping("/admin/newUser")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User()); // Provide an empty User object
        return "new-user-form"; // Thymeleaf template for creating a new user
    }

    @PostMapping("/admin/newUser")
    public String createUser(User user) {
        userService.saveNewUser(user); // Save the new user
        return "redirect:/admin"; // Redirect back to the admin dashboard
    }


    @GetMapping("/admin/newBook")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book()); // Provide an empty Book object
        return "new-book-form"; // Thymeleaf template for creating a new book
    }

    @PostMapping("/admin/newBook")
    public String createBook(Book book) {
        bookService.saveNewBook(book); // Save the new book
        return "redirect:/admin"; // Redirect back to the admin dashboard
    }

    @PostMapping("/admin/deleteTransaction/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id); // Delete the transaction using the service
        return "redirect:/admin"; // Redirect back to the admin dashboard
    }

}
