package com.finance.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;

public interface ITransactionDao {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByDate(Date date);

    List<Transaction> getTransactionsByCategory(TransactionCategory category);

    List<Transaction> getTransactionsByType(TransactionType type);

    HashMap<TransactionCategory, Double> getCategoryTotals();

    boolean addTransaction(Transaction transaction);

    boolean updateTransaction(Transaction transaction);

    boolean deleteTransaction(int id);

}
