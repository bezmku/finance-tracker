package com.finance.ui.listener;

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
import com.finance.ui.MainFrame;
import com.finance.ui.customui.custombutton.RoundButton;
import com.finance.ui.customui.customcombobox.CustomComboBoxUI;
import com.finance.ui.customui.customcombobox.CustomComoboBoxRenderer;

public class OnUpdate implements Runnable {

    private TransactionService service;
    private boolean success = false;
    private JComboBox<TransactionType> typeCombo;
    private JComboBox<TransactionCategory> catCombo;
    private JTextField amountField;
    private JTextField descField;
    JDialog updateDialog;
    private MainFrame mainFrame;
    private Transaction transaction;

    public OnUpdate(MainFrame mainFrame, Transaction transaction) {
        this.mainFrame = mainFrame;
        this.transaction = transaction;
    }

    @Override
    public void run() {

        JDialog updateDialog = new JDialog(mainFrame, "Update Transaction", true);
        updateDialog.setLocationRelativeTo(null);
        updateDialog.setSize(400, 350);
        updateDialog.setLayout(new BorderLayout());

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));

        // Type label + combo
        JPanel typeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setFont(AppTheme.BODY_FONT);
        typeCombo.setUI(new CustomComboBoxUI());
        typeCombo.setRenderer(new CustomComoboBoxRenderer());
        typeCombo.setSelectedItem(transaction.getType());

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
        catCombo.setSelectedItem(transaction.getCategory());

        catRow.add(new JLabel("Category:"));
        catRow.add(Box.createHorizontalStrut(20));
        catRow.add(catCombo);
        fields.add(catRow);

        // Amount
        JPanel amountRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        amountField = new JTextField(15);
        amountField.setFont(AppTheme.BODY_FONT);
        amountField.setText(String.format("%.2f", transaction.getAmount()));

        amountRow.add(new JLabel("Amount:"));
        amountRow.add(Box.createHorizontalStrut(20));
        amountRow.add(amountField);

        fields.add(amountRow);

        // Description
        JPanel descRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        descField = new JTextField(25);
        descField.setPreferredSize(new Dimension(300, 50));
        descField.setFont(AppTheme.BODY_FONT);
        descField.setText(transaction.getDescription());

        descRow.add(new JLabel("Description:"));
        descRow.add(Box.createHorizontalStrut(20));
        descRow.add(descField);
        fields.add(descRow);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        RoundButton updateButton = new RoundButton("update", 10);
        updateButton.setFont(AppTheme.BUTTON_FONT);
        updateButton.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));
        updateButton.setBackground(AppTheme.INCOME_COLOR);
        updateButton.setForeground(AppTheme.TEXT_ON_ACCENT);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> {
            updateTransaction();
            mainFrame.loadTransaction();
            updateDialog.dispose();
        });
        buttons.add(updateButton);
        buttons.add(Box.createHorizontalStrut(10));

        RoundButton cancelButton = new RoundButton("Cancel", 10);
        cancelButton.setFont(AppTheme.BUTTON_FONT);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));
        cancelButton.setBackground(AppTheme.EXPENSE_COLOR);
        cancelButton.setForeground(AppTheme.TEXT_ON_ACCENT);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> updateDialog.dispose());
        buttons.add(cancelButton);
        buttons.add(Box.createHorizontalStrut(10));
        updateDialog.add(buttons, BorderLayout.SOUTH);

        updateDialog.add(fields, BorderLayout.CENTER);
        updateDialog.setVisible(true);

    }

    private void updateTransaction() {
        TransactionType type = (TransactionType) typeCombo.getSelectedItem();
        TransactionCategory category = (TransactionCategory) catCombo.getSelectedItem();
        String description = descField.getText();

        String StringAmount = amountField.getText();
        double amount;

        try {
            amount = Double.parseDouble(StringAmount);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(updateDialog, "Invalid amount");
                return;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(updateDialog, "Invalid amount");
            return;
        }

        Transaction updated = new Transaction(transaction.getId(), type, category, description, amount,
                transaction.getDate());
        service = new TransactionService();

        success = service.updateTransaction(updated);
        if (success) {
            JOptionPane.showMessageDialog(updateDialog, "Transaction updated successfully");

        } else {
            JOptionPane.showMessageDialog(updateDialog, "Transaction not updated");
        }

    }

}
