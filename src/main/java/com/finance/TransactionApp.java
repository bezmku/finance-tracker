package com.finance;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import com.finance.model.*;
import com.finance.ui.TransactionCard;

public class TransactionApp {
    public static void main(String[] args) {
        // Create a test transaction
        Transaction t = new Transaction(1, TransactionType.INCOME,
                TransactionCategory.SALARY,
                "Monthly salary deposit",
                250.00,
                new Date());

        // Build a frame to show it
        JFrame frame = new JFrame("Test Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0xE3F2FD));
        frame.setLayout(new FlowLayout());
        frame.add(new TransactionCard(t, null, null)); // no listeners yet
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
