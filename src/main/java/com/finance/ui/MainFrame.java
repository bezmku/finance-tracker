package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;
import com.finance.service.TransactionService;
import com.finance.theme.AppTheme;
import com.finance.ui.customui.custombutton.RoundButton;
import com.finance.ui.customui.customcombobox.CustomComboBoxUI;
import com.finance.ui.customui.customcombobox.CustomComoboBoxRenderer;
import com.finance.ui.listener.UIListener;

public class MainFrame extends JFrame {

    private JPanel cardPanel;
    private JComboBox<String> filterCombo;
    private JComboBox<TransactionCategory> catCombo;
    private JComboBox<TransactionType> typeCombo;
    private JComboBox<String> periodCombo;

    // Getters
    public JComboBox<String> getFilterCombo() {
        return filterCombo;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public JComboBox<TransactionCategory> getCatCombo() {
        return catCombo;
    }

    public JComboBox<TransactionType> getTypeCombo() {
        return typeCombo;
    }

    public JComboBox<String> getPeriodCombo() {
        return periodCombo;
    }


    // Constructor
    public MainFrame() {
        setTitle("Transaction Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);




        UIListener listener = new UIListener(this);
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));

        // Toolbar
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BorderLayout());
        toolbar.setBackground(AppTheme.PANEL_BG);
        toolbar.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));

        RoundButton addButton = new RoundButton("Add +", 10);
        addButton.setFont(AppTheme.BUTTON_FONT);
        addButton.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));
        addButton.setBackground(AppTheme.ACCENT_PRIMARY);
        addButton.setForeground(AppTheme.TEXT_ON_ACCENT);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            AddDialogue dialog = new AddDialogue(this);
            dialog.setVisible(true);
            loadTransaction();
        });

        toolbar.add(addButton, BorderLayout.WEST);
        toolbar.add(Box.createHorizontalStrut(10));

        JPanel filters = new JPanel(new GridLayout(2, 2, 12, 6));
        filters.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        filters.setOpaque(false);
        JLabel filterBy = new JLabel("Filter by:");
        filterBy.setFont(AppTheme.BODY_FONT);
        filters.add(filterBy);

        filterCombo = new JComboBox<>(new String[] { "All", "CATEGORY", "TYPE", "DATE" });
        filterCombo.setFont(AppTheme.BODY_FONT);
        filterCombo.setUI(new CustomComboBoxUI());
        filterCombo.setRenderer(new CustomComoboBoxRenderer());
        filterCombo.addActionListener(e -> listener.filterTransaction());

        
        filters.add(filterCombo);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(AppTheme.BODY_FONT);
        filters.add(typeLabel);

        typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setFont(AppTheme.BODY_FONT);
        typeCombo.setUI(new CustomComboBoxUI());
        typeCombo.setRenderer(new CustomComoboBoxRenderer());
        typeCombo.addActionListener(e -> listener.filterTransaction());

        filters.add(typeCombo);

        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(AppTheme.BODY_FONT);
        filters.add(catLabel);

        catCombo = new JComboBox<>(TransactionCategory.values());
        catCombo.setFont(AppTheme.BODY_FONT);
        catCombo.setUI(new CustomComboBoxUI());
        catCombo.setRenderer(new CustomComoboBoxRenderer());
        catCombo.addActionListener(e -> listener.filterTransaction());

        filters.add(catCombo);

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setFont(AppTheme.BODY_FONT);
        filters.add(periodLabel);

        periodCombo = new JComboBox<>(new String[] { "All", "Today", "This Week", "This Month" });
        periodCombo.setFont(AppTheme.BODY_FONT);
        periodCombo.setUI(new CustomComboBoxUI());
        periodCombo.setRenderer(new CustomComoboBoxRenderer());
        periodCombo.addActionListener(e -> listener.filterTransaction());

        filters.add(periodCombo);

        toolbar.add(filters);

        // Card/Transaction list
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(AppTheme.BG_COLOR);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING,
                AppTheme.PADDING,
                AppTheme.PADDING,
                AppTheme.PADDING));

        loadTransaction();
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

    public void loadTransaction() {
        cardPanel.removeAll();
        cardPanel.revalidate();
        cardPanel.repaint();
        TransactionService transactionService = new TransactionService();
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.size() == 0) {
            JLabel label = new JLabel("No transactions found");
            label.setFont(AppTheme.TITLE_FONT);
            label.setForeground(AppTheme.TEXT_SECONDARY);
            cardPanel.add(label);
        } else {
            for (Transaction t : transactions) {
                TransactionCard card = new TransactionCard(t, null, null);
                cardPanel.add(card);
            }
        }

    }

}
