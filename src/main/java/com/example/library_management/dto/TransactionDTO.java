package com.example.library_management.dto;

import java.util.Date;

public class TransactionDTO {

    private Long id;
    private String userName;
    private String bookTitle;
    private String action;
    private Date date;

    public TransactionDTO(Long id, String userName, String bookTitle, String action, Date date) {
        this.id = id;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.action = action;
        this.date = date;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
