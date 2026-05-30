package com.finance.service;

import java.util.Date;
import java.util.List;

import com.finance.dao.ITransactionDao;
import com.finance.dao.TransactionDao;
import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;

public class TransactionService {
    private ITransactionDao dao;

    public TransactionService() {
        this.dao = new TransactionDao();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = dao.getAllTransactions();
        return transactions != null ? transactions : List.of();
    }

    public List<Transaction> getTransactionsByType(TransactionType type) {
        List<Transaction> transactions = dao.getTransactionsByType(type);
        return transactions != null ? transactions : List.of();
    }

    public List<Transaction> getTransactionsByCategory(TransactionCategory category) {
        List<Transaction> transactions = dao.getTransactionsByCategory(category);
        return transactions != null ? transactions : List.of();
    }

    public List<Transaction> getTransactionsByDate(Date date) {
        List<Transaction> transactions = dao.getTransactionsByDate(date);
        return transactions != null ? transactions : List.of();
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null)
            return false;
        if (transaction.getAmount() <= 0)
            return false;
        if (transaction.getDescription() == null || transaction.getDescription().isBlank())
            return false;
        return dao.addTransaction(transaction);
    }

    public boolean updateTransaction(Transaction transaction) {
        if (transaction == null || transaction.getId() <= 0)
            return false;
        if (transaction.getAmount() <= 0)
            return false;
        if (transaction.getDescription() == null || transaction.getDescription().isBlank())
            return false;
        return dao.updateTransaction(transaction);
    }

    public boolean deleteTransaction(int id) {
        return id > 0 && dao.deleteTransaction(id);
    }
}
