package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.finance.model.Transaction;
import com.finance.model.TransactionType;
import com.finance.theme.AppTheme;

public class TransactionCard extends JPanel {
    private Transaction transaction;
    private Runnable onUpdate;
    private Runnable onDelete;

    public TransactionCard(Transaction t, Runnable onUpdate, Runnable onDelete) {
        this.transaction = t;
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;
        createCard();
    }

    private void createCard() {
        setLayout(new BorderLayout(AppTheme.GAP, 0));
        setPreferredSize(new Dimension(AppTheme.CARD_WIDTH, AppTheme.CARD_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setOpaque(false); // prevents painting of default bg
        setBorder(BorderFactory.createEmptyBorder(AppTheme.PADDING, AppTheme.PADDING, AppTheme.PADDING,
                AppTheme.PADDING));

        // Left: type indicator green dot for income and red dor for expense
        JPanel typeIndicator = new JPanel();
        typeIndicator.setOpaque(false);
        typeIndicator.setPreferredSize(new Dimension(12, 0));
        typeIndicator.setLayout(new GridBagLayout());

        JPanel dot = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(transaction.getType() == TransactionType.INCOME ? AppTheme.INCOME_COLOR
                        : AppTheme.EXPENSE_COLOR);
                g2.fillOval(0, 0, 10, 10);
                g2.dispose();
            }
        };
        dot.setOpaque(false);
        dot.setPreferredSize(new Dimension(10, 10));
        typeIndicator.add(dot);
        add(typeIndicator, BorderLayout.WEST);

        // Center: details panel
        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        // Row 1: amount and category
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row1.setOpaque(false);
        String sign = transaction.getType() == TransactionType.INCOME ? "+" : "-";
        String amountText = sign + "$" + String.format("%.2f", transaction.getAmount());
        JLabel amountLabel = new JLabel(amountText);
        amountLabel.setFont(AppTheme.TITLE_FONT);
        amountLabel.setForeground(
                transaction.getType() == TransactionType.INCOME ? AppTheme.INCOME_COLOR : AppTheme.EXPENSE_COLOR);
        row1.add(amountLabel);
        JLabel sep = new JLabel("  ·  ");
        sep.setFont(AppTheme.TITLE_FONT);
        sep.setForeground(AppTheme.TEXT_SECONDARY);
        row1.add(sep);

        JLabel categoryLabel = new JLabel(transaction.getCategory().name());
        categoryLabel.setFont(AppTheme.BODY_FONT);
        categoryLabel.setForeground(AppTheme.TEXT_SECONDARY);
        row1.add(categoryLabel);

        details.add(row1);

        // Row 2: description and date

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row2.setOpaque(false);
        String desc = transaction.getDescription();
        if (desc.length() > 25)
            desc = desc.substring(0, 22) + "...";

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(AppTheme.SMALL_FONT);
        descLabel.setForeground(AppTheme.TEXT_COLOR);
        row2.add(descLabel);

        JLabel dateLabel = new JLabel("  |  " + String.format("%1$tB %1$td, %1$tY", transaction.getDate()));
        dateLabel.setFont(AppTheme.SMALL_FONT);
        dateLabel.setForeground(AppTheme.TEXT_SECONDARY);
        row2.add(dateLabel);

        details.add(row2);

        add(details, BorderLayout.CENTER);

        // Right: update and delete buttons

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        btnPanel.setOpaque(false);

        JButton updateBtn = new JButton("update");
        updateBtn.setFont(AppTheme.BUTTON_FONT);
        updateBtn.setBackground(AppTheme.ACCENT_PRIMARY);
        updateBtn.setForeground(AppTheme.TEXT_ON_ACCENT);
        updateBtn.setFocusPainted(false);
        updateBtn.setBorderPainted(false);
        updateBtn.setOpaque(true);
        updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtn.addActionListener(e -> {
            if (onUpdate != null)
                onUpdate.run();
        });

        JButton deleteBtn = new JButton("delete");
        deleteBtn.setFont(AppTheme.BUTTON_FONT);
        deleteBtn.setBackground(AppTheme.EXPENSE_COLOR);
        deleteBtn.setForeground(AppTheme.TEXT_ON_ACCENT);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setOpaque(true);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            if (onDelete != null)
                onDelete.run();
        });

        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.EAST);

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(AppTheme.CARD_BG);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 40));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
    }

}
