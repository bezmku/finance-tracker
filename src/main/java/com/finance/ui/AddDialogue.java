package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;
import com.finance.service.TransactionService;
import com.finance.theme.AppTheme;
import com.finance.ui.customui.custombutton.RoundButton;
import com.finance.ui.customui.customcombobox.CustomComboBoxUI;
import com.finance.ui.customui.customcombobox.CustomComoboBoxRenderer;
import com.finance.ui.listener.Filter;

public class AddDialogue extends JDialog {

    private TransactionService service;
    private boolean success = false;
    private JComboBox<TransactionType> typeCombo;
    private JComboBox<TransactionCategory> catCombo;
    private JTextField amountField;
    private JTextField descField;
    private MainFrame mainFrame;

    public AddDialogue(MainFrame mainFrame) {
        super(mainFrame, "Add Transaction", true);
        this.mainFrame = mainFrame;
        setLocationRelativeTo(null);
        setSize(400, 350);
        setLayout(new BorderLayout());

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));

        // Type label + combo
        JPanel typeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setFont(AppTheme.BODY_FONT);
        typeCombo.setUI(new CustomComboBoxUI());
        typeCombo.setRenderer(new CustomComoboBoxRenderer());

        typeRow.add(new JLabel("Type:"));
        typeRow.add(Box.createHorizontalStrut(20));
        typeRow.add(typeCombo);
        fields.add(typeRow);

        // Category
        JPanel catRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        catCombo = new JComboBox<>(TransactionCategory.values());
        catCombo.setFont(AppTheme.BODY_FONT);
        catCombo.setUI(new CustomComboBoxUI());
        catCombo.setRenderer(new CustomComoboBoxRenderer());

        catRow.add(new JLabel("Category:"));
        catRow.add(Box.createHorizontalStrut(20));
        catRow.add(catCombo);
        fields.add(catRow);

        // Amount
        JPanel amountRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        amountField = new JTextField(15);
        amountField.setFont(AppTheme.BODY_FONT);
        amountField.setText("0.00");

        amountRow.add(new JLabel("Amount:"));
        amountRow.add(Box.createHorizontalStrut(20));
        amountRow.add(amountField);

        fields.add(amountRow);

        // Description
        JPanel descRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        descField = new JTextField(25);
        descField.setPreferredSize(new Dimension(300, 50));
        descField.setFont(AppTheme.BODY_FONT);
        descField.setText("Description");

        descRow.add(new JLabel("Description:"));
        descRow.add(Box.createHorizontalStrut(20));
        descRow.add(descField);
        fields.add(descRow);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        RoundButton saveButtone = new RoundButton("Save", 10);
        saveButtone.setFont(AppTheme.BUTTON_FONT);
        saveButtone.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));
        saveButtone.setBackground(AppTheme.INCOME_COLOR);
        saveButtone.setForeground(AppTheme.TEXT_ON_ACCENT);
        saveButtone.setFocusPainted(false);
        saveButtone.addActionListener(e -> {
            saveTransaction();
        });
        buttons.add(saveButtone);
        buttons.add(Box.createHorizontalStrut(10));

        RoundButton cancelButton = new RoundButton("Cancel", 10);
        cancelButton.setFont(AppTheme.BUTTON_FONT);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));
        cancelButton.setBackground(AppTheme.EXPENSE_COLOR);
        cancelButton.setForeground(AppTheme.TEXT_ON_ACCENT);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(10));
        add(buttons, BorderLayout.SOUTH);

        add(fields, BorderLayout.CENTER);

    }

    private void saveTransaction() {
        TransactionType type = (TransactionType) typeCombo.getSelectedItem();
        TransactionCategory category = (TransactionCategory) catCombo.getSelectedItem();
        if (type == TransactionType.ALL || category == TransactionCategory.ALL) {
            JOptionPane.showMessageDialog(this, "Invalid category or type");
            return;
        }
        String description = descField.getText();

        String StringAmount = amountField.getText();
        double amount;

        try {
            amount = Double.parseDouble(StringAmount);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid amount");
                return;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount");
            return;
        }

        Transaction transaction = new Transaction(type, category, description, amount);
        service = new TransactionService();

        success = service.addTransaction(transaction);
        if (success) {
            JOptionPane.showMessageDialog(this, "Transaction added successfully");
            Filter filter = new Filter(mainFrame);
            filter.filterTransaction();
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Transaction not added");
        }

    }

}
