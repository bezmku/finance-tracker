package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;
import com.finance.theme.AppTheme;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Transaction Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));

        // Toolbar
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        toolbar.setBackground(AppTheme.PANEL_BG);
        toolbar.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));

        JButton addButton = new JButton("Add");
        addButton.setFont(AppTheme.BUTTON_FONT);
        addButton.setBackground(AppTheme.ACCENT_PRIMARY);
        addButton.setForeground(AppTheme.TEXT_ON_ACCENT);
        addButton.setFocusPainted(false);
        toolbar.add(addButton);
        toolbar.add(Box.createHorizontalStrut(20));
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(AppTheme.BODY_FONT);
        toolbar.add(typeLabel);

        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setFont(AppTheme.BODY_FONT);

        toolbar.add(typeCombo);
        toolbar.add(Box.createHorizontalStrut(20));

        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(AppTheme.BODY_FONT);
        toolbar.add(catLabel);

        JComboBox<TransactionCategory> catCombo = new JComboBox<>(TransactionCategory.values());
        catCombo.setFont(AppTheme.BODY_FONT);
        toolbar.add(catCombo);
        toolbar.add(Box.createHorizontalStrut(20));

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(AppTheme.BODY_FONT);
        toolbar.add(periodLabel);

        JComboBox<String> periodCombo = new JComboBox<>(new String[] { "All", "Today", "This Week", "This Month" });
        periodCombo.setFont(AppTheme.BODY_FONT);
        toolbar.add(periodCombo);

        // Card/Transaction list
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(AppTheme.BG_COLOR);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING,
                AppTheme.PADDING,
                AppTheme.PADDING,
                AppTheme.PADDING));

        Transaction t = new Transaction(1, TransactionType.INCOME,
                TransactionCategory.SALARY,
                "Monthly salary deposit",
                250.00,
                new Date());
        Transaction t2 = new Transaction(2, TransactionType.EXPENSE,
                TransactionCategory.FOOD,
                "Lunch with coffee",
                250.00,
                new Date());

        for (int i = 0; i < 10; i++) {
            TransactionCard card = new TransactionCard(t, null, null);
            cardPanel.add(card);
        }
        for (int i = 0; i < 10; i++) {
            TransactionCard card = new TransactionCard(t2, null, null);
            cardPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBackground(AppTheme.BG_COLOR);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(toolbar, BorderLayout.NORTH);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(new Color(255, 255, 255, 200));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 15, 15));

        // Header
        JLabel summaryHeader = new JLabel("SUMMARY");
        summaryHeader.setFont(AppTheme.TITLE_FONT);
        summaryHeader.setForeground(AppTheme.ACCENT_PRIMARY);
        summaryHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(summaryHeader);
        sidebar.add(Box.createVerticalStrut(15));
        JPanel summarySection = new JPanel();
        summarySection.setLayout(new BoxLayout(summarySection, BoxLayout.Y_AXIS));
        summarySection.setOpaque(false);

        // Income
        JPanel incomeRow = new JPanel(new BorderLayout());
        incomeRow.setOpaque(false);
        JLabel incomeLabel = new JLabel("Income");
        incomeLabel.setFont(AppTheme.BODY_FONT);
        incomeRow.add(incomeLabel, BorderLayout.WEST);
        JLabel incomeValue = new JLabel("$0.00");
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
        JLabel expenseValue = new JLabel("$0.00");
        expenseValue.setForeground(AppTheme.EXPENSE_COLOR);
        expenseValue.setFont(AppTheme.TITLE_FONT);
        expenseRow.add(expenseValue, BorderLayout.EAST);
        summarySection.add(expenseRow);
        summarySection.add(Box.createVerticalStrut(3));

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0, 0, 0, 40));
        sidebar.add(Box.createVerticalStrut(3));
        summarySection.add(sep);
        summarySection.add(Box.createVerticalStrut(3));

        // Balance
        JPanel balanceRow = new JPanel(new BorderLayout());
        balanceRow.setOpaque(false);
        JLabel balanceLabel = new JLabel("Balance");
        balanceLabel.setFont(AppTheme.BODY_FONT);
        balanceRow.add(balanceLabel, BorderLayout.WEST);
        JLabel balanceValue = new JLabel("$0.00");
        balanceValue.setForeground(AppTheme.ACCENT_PRIMARY);
        balanceValue.setFont(AppTheme.TITLE_FONT);
        balanceRow.add(balanceValue, BorderLayout.EAST);
        summarySection.add(balanceRow);

        sidebar.add(summarySection);

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
        sidebar.add(topCategories);

        add(mainPanel, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);

        setVisible(true);

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
