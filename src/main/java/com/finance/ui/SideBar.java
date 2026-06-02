package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.finance.model.Transaction;
import com.finance.model.TransactionType;
import com.finance.service.TransactionService;
import com.finance.theme.AppTheme;

public class SideBar extends JPanel {
    private JLabel incomeValue;
    private JLabel expenseValue;
    private JLabel balanceValue;
    private JPanel summarySection;

    public SideBar() {
        // Sidebar
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(230, 0));
        setBackground(new Color(255, 255, 255, 200));
        setBorder(BorderFactory.createEmptyBorder(20, 15, 15, 15));

        // Header
        JLabel summaryHeader = new JLabel("SUMMARY");
        summaryHeader.setFont(AppTheme.TITLE_FONT);
        summaryHeader.setForeground(AppTheme.ACCENT_PRIMARY);
        summaryHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(5));
        add(summaryHeader);
        add(Box.createVerticalStrut(15));
        summarySection = new JPanel();
        summarySection.setLayout(new BoxLayout(summarySection, BoxLayout.Y_AXIS));
        summarySection.setOpaque(false);

        // Income

        JPanel incomeRow = new JPanel(new BorderLayout());
        incomeRow.setOpaque(false);
        JLabel incomeLabel = new JLabel("Income");
        incomeLabel.setFont(AppTheme.BODY_FONT);
        incomeRow.add(incomeLabel, BorderLayout.WEST);
        double income = getTotal(TransactionType.INCOME);
        incomeValue = new JLabel(String.format("$%.2f", income));
        incomeValue.setForeground(AppTheme.INCOME_COLOR);
        incomeValue.setFont(AppTheme.TITLE_FONT);
        incomeRow.add(incomeValue, BorderLayout.EAST);
        summarySection.add(incomeRow);
        summarySection.add(Box.createVerticalStrut(3));

        // Expense
        JPanel expenseRow = new JPanel(new BorderLayout());
        expenseRow.setOpaque(false);
        JLabel expenseLabel = new JLabel("Expense");
        expenseLabel.setFont(AppTheme.BODY_FONT);
        expenseRow.add(expenseLabel, BorderLayout.WEST);
        double expense = getTotal(TransactionType.EXPENSE);
        expenseValue = new JLabel(String.format("$%.2f", expense));
        expenseValue.setForeground(AppTheme.EXPENSE_COLOR);
        expenseValue.setFont(AppTheme.TITLE_FONT);
        expenseRow.add(expenseValue, BorderLayout.EAST);
        summarySection.add(expenseRow);
        summarySection.add(Box.createVerticalStrut(3));

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 0, 0, 40));
        add(Box.createVerticalStrut(3));
        summarySection.add(sep);
        summarySection.add(Box.createVerticalStrut(3));

        // Balance
        JPanel balanceRow = new JPanel(new BorderLayout());
        balanceRow.setOpaque(false);
        JLabel balanceLabel = new JLabel("Balance");
        balanceLabel.setFont(AppTheme.BODY_FONT);
        balanceRow.add(balanceLabel, BorderLayout.WEST);
        double balance = income - expense;
        balanceValue = new JLabel(String.format("$%.2f", balance));
        balanceValue.setForeground((balance > 0) ? AppTheme.INCOME_COLOR : AppTheme.EXPENSE_COLOR);
        balanceValue.setFont(AppTheme.TITLE_FONT);
        balanceRow.add(balanceValue, BorderLayout.EAST);
        summarySection.add(balanceRow);

        add(summarySection);

        // Top categories
        JPanel topCategories = new JPanel();

        topCategories.setLayout(new BoxLayout(topCategories, BoxLayout.Y_AXIS));
        topCategories.setOpaque(false);
        topCategories.setBackground(AppTheme.BG_COLOR);
        topCategories.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        JLabel topCategoriesLabel = new JLabel("TOP CATEGORIES");
        topCategoriesLabel.setFont(AppTheme.BODY_FONT);
        topCategoriesLabel.setForeground(AppTheme.TEXT_SECONDARY);
        topCategories.add(topCategoriesLabel);

        topCategories.add(Box.createVerticalStrut(8));

        String[][] cats = { { "Salary", "$2,500.00" }, { "Food", "$800.00" }, { "Rent", "$600.00" } };
        for (String[] cat : cats) {
            JPanel catRow = new JPanel(new BorderLayout());
            catRow.setOpaque(false);
            JLabel name = new JLabel("  ·  " + cat[0]);
            name.setFont(AppTheme.TITLE_FONT);
            catRow.add(name, BorderLayout.WEST);
            JLabel val = new JLabel(cat[1]);
            val.setFont(AppTheme.TITLE_FONT);
            val.setForeground(AppTheme.TEXT_SECONDARY);
            catRow.add(val, BorderLayout.EAST);
            topCategories.add(catRow);
            topCategories.add(Box.createVerticalStrut(4));
        }
        add(topCategories);

    }

    public double getTotal(TransactionType type) {

        TransactionService ts = new TransactionService();
        List<Transaction> transactions = ts.getAllTransactions();
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getType() == type) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public void refreshSidebar() {
        double income = getTotal(TransactionType.INCOME);
        incomeValue.setText(String.format("$%.2f", income));

        double expense = getTotal(TransactionType.EXPENSE);
        expenseValue.setText(String.format("$%.2f", expense));

        double balance = income - expense;
        balanceValue.setText(String.format("$%.2f", balance));
        balanceValue.setForeground((balance > 0) ? AppTheme.INCOME_COLOR : AppTheme.EXPENSE_COLOR);

        revalidate();
        repaint();

    }

}
