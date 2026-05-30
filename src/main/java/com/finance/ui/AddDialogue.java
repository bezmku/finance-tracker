package com.finance.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import com.finance.model.*;
import com.finance.theme.AppTheme;

public class AddDialogue extends JDialog {
    private JComboBox<TransactionType> typeCombo;
    private JComboBox<TransactionCategory> categoryCombo;
    private JTextField descriptionField;
    private JTextField amountField;
    private JSpinner dateSpinner;
    private Transaction result;
    private boolean saved;

    public AddDialogue(JFrame parent) {
        super(parent, "Add Transaction", true);
        setSize(420, 360);
        setLocationRelativeTo(parent);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppTheme.CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(AppTheme.BODY_FONT);
        panel.add(typeLabel, gbc);

        gbc.gridx = 1;
        typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setFont(AppTheme.BODY_FONT);
        panel.add(typeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(AppTheme.BODY_FONT);
        panel.add(catLabel, gbc);

        gbc.gridx = 1;
        categoryCombo = new JComboBox<>(TransactionCategory.values());
        categoryCombo.setFont(AppTheme.BODY_FONT);
        panel.add(categoryCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(AppTheme.BODY_FONT);
        panel.add(descLabel, gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        descriptionField.setFont(AppTheme.BODY_FONT);
        panel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel amtLabel = new JLabel("Amount:");
        amtLabel.setFont(AppTheme.BODY_FONT);
        panel.add(amtLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(20);
        amountField.setFont(AppTheme.BODY_FONT);
        panel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(AppTheme.BODY_FONT);
        panel.add(dateLabel, gbc);

        gbc.gridx = 1;
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MMM dd, yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        dateSpinner.setFont(AppTheme.BODY_FONT);
        panel.add(dateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setOpaque(false);

        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(AppTheme.BUTTON_FONT);
        saveBtn.setBackground(AppTheme.ACCENT_PRIMARY);
        saveBtn.setForeground(AppTheme.TEXT_ON_ACCENT);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setOpaque(true);
        saveBtn.addActionListener(e -> save());

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(AppTheme.BUTTON_FONT);
        cancelBtn.setBackground(AppTheme.EXPENSE_COLOR);
        cancelBtn.setForeground(AppTheme.TEXT_ON_ACCENT);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setOpaque(true);
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        panel.add(btnPanel, gbc);

        add(panel);
    }

    private void save() {
        String desc = descriptionField.getText().trim();
        String amtText = amountField.getText().trim();

        if (desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (amtText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Amount cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amtText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TransactionType type = (TransactionType) typeCombo.getSelectedItem();
        TransactionCategory category = (TransactionCategory) categoryCombo.getSelectedItem();
        Date date = (Date) dateSpinner.getValue();

        result = new Transaction(type, category, desc, amount, date);
        saved = true;
        dispose();
    }

    public Transaction getTransaction() {
        return result;
    }

    public boolean isSaved() {
        return saved;
    }
}
