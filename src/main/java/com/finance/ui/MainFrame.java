package com.finance.ui;

import java.awt.BorderLayout;
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

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;
import com.finance.service.TransactionService;
import com.finance.theme.AppTheme;
import com.finance.ui.customui.custombutton.RoundButton;
import com.finance.ui.customui.customcombobox.CustomComboBoxUI;
import com.finance.ui.customui.customcombobox.CustomComoboBoxRenderer;
import com.finance.ui.listener.Filter;
import com.finance.ui.listener.OnDelete;
import com.finance.ui.listener.OnUpdate;

public class MainFrame extends JFrame {

    private JPanel cardPanel;
    private JComboBox<String> filterCombo;
    private JComboBox<TransactionCategory> catCombo;
    private JComboBox<TransactionType> typeCombo;
    private JComboBox<String> periodCombo;
    private SideBar sideBar;

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

        Filter listener = new Filter(this);

        // SideBar
        sideBar = new SideBar();

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

        filterCombo = new JComboBox<>(new String[] { "ALL", "CATEGORY", "TYPE", "DATE" });
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

        periodCombo = new JComboBox<>(new String[] { "ALL", "Today", "This Week", "This Month" });
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(AppTheme.BG_COLOR);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(toolbar, BorderLayout.NORTH);

        add(mainPanel, BorderLayout.CENTER);
        add(sideBar, BorderLayout.EAST);

        setVisible(true);

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
                OnUpdate onUpdate = new OnUpdate(this, t);
                OnDelete onDelete = new OnDelete(this, t);
                TransactionCard card = new TransactionCard(t, onUpdate, onDelete);
                cardPanel.add(card);
            }
        }

        sideBar.refreshSidebar();
    }

}
