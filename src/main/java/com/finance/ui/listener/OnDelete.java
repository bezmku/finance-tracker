package com.finance.ui.listener;

import javax.swing.JOptionPane;

import com.finance.model.Transaction;
import com.finance.service.TransactionService;
import com.finance.ui.MainFrame;

public class OnDelete implements Runnable {

    private Transaction transaction;
    private MainFrame mainFrame;
    private boolean isDeleted = false;

    public OnDelete(MainFrame mainFrame, Transaction transaction) {
        this.transaction = transaction;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {

        if (JOptionPane.showConfirmDialog(mainFrame, "Are you sure you delete the transaction?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            TransactionService transactionService = new TransactionService();
            isDeleted = transactionService.deleteTransaction(transaction.getId());

            if (isDeleted) {
                JOptionPane.showMessageDialog(mainFrame, "Transaction Deleted!!");
                mainFrame.loadTransaction();
            } else
                JOptionPane.showMessageDialog(mainFrame, "Transaction not Deleted");
        } else
            return;

    }

}
