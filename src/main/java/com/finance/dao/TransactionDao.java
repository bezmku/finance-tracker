package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.finance.database.DatabaseConnection;
import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;

public class TransactionDao implements ITransactionDao {

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (type, category, description, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getType().name());
            ps.setString(2, transaction.getCategory().name());
            ps.setString(3, transaction.getDescription());
            ps.setDouble(4, transaction.getAmount());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET type = ?, category = ?, description = ?, amount = ? WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getType().name());
            ps.setString(2, transaction.getCategory().name());
            ps.setString(3, transaction.getDescription());
            ps.setDouble(4, transaction.getAmount());
            ps.setInt(6, transaction.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions ORDER BY id DESC";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            List<Transaction> transactions = new java.util.ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(TransactionType.valueOf(rs.getString("type")));
                transaction.setCategory(TransactionCategory.valueOf(rs.getString("category")));
                transaction.setDescription(rs.getString("description"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getDate("date"));
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transaction> getTransactionsByDate(Date date) {

        String sql = "SELECT * FROM transactions WHERE date = ?";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(date.getTime()));
            List<Transaction> transactions = new java.util.ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setCategory(TransactionCategory.valueOf(rs.getString("category")));
                transaction.setDate(rs.getDate("date"));
                transaction.setDescription(rs.getString("description"));
                transaction.setType(TransactionType.valueOf(rs.getString("type")));
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transaction> getTransactionsByCategory(TransactionCategory category) {
        String sql = "SELECT * FROM transactions WHERE category = ?";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.name());
            List<Transaction> transactions = new java.util.ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(TransactionType.valueOf(rs.getString("type")));
                transaction.setCategory(TransactionCategory.valueOf(rs.getString("category")));
                transaction.setDescription(rs.getString("description"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getDate("date"));
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        String sql = "SELECT * FROM transactions WHERE type = ?";
        try (Connection conn = databaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type.name());
            List<Transaction> transactions = new java.util.ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(TransactionType.valueOf(rs.getString("type")));
                transaction.setCategory(TransactionCategory.valueOf(rs.getString("category")));
                transaction.setDescription(rs.getString("description"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setDate(rs.getDate("date"));
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
