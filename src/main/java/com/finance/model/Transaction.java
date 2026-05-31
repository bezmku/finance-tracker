package com.finance.model;

import java.util.Date;

public class Transaction {

    private int id;
    private TransactionType type;
    private TransactionCategory category;
    private String description;
    private double amount;
    private Date date;

    public Transaction() {
    }

    // Constructor for creating a new transaction

    public Transaction(int id, TransactionType type, TransactionCategory category, String description, double amount,
            Date date) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    // Constructor for updating a transaction

    public Transaction(TransactionType type, TransactionCategory category, String description, double amount) {
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
