package com.finance;

import javax.swing.SwingUtilities;

import com.finance.ui.MainFrame;

public class TransactionApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
