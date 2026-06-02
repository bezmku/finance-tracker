package com.finance.ui.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;

import com.finance.model.Transaction;
import com.finance.model.TransactionCategory;
import com.finance.model.TransactionType;
import com.finance.service.TransactionService;
import com.finance.theme.AppTheme;
import com.finance.ui.MainFrame;
import com.finance.ui.TransactionCard;

public class Filter {
    MainFrame mainFrame;
    private TransactionService transactionService;

    public Filter(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.transactionService = new TransactionService();

    }

    public void filterTransaction() {

        List<Transaction> transactions = transactionService.getAllTransactions();

        String filter = (String) mainFrame.getFilterCombo().getSelectedItem();
        if ("ALL".equals(filter)) {
            transactions = transactionService.getAllTransactions();
        }

        else if ("CATEGORY".equals(filter)) {
            TransactionCategory cat = (TransactionCategory) mainFrame.getCatCombo().getSelectedItem();
            transactions = transactionService.getTransactionsByCategory(cat);
        }

        else if ("TYPE".equals(filter)) {
            TransactionType type = (TransactionType) mainFrame.getTypeCombo().getSelectedItem();
            transactions = transactionService.getTransactionsByType(type);
        }

        else if ("DATE".equals(filter)) {

            String period = (String) mainFrame.getPeriodCombo().getSelectedItem();
            if ("Today".equals(period)) {
                transactions = transactionService.getTransactionsByDate(new Date());
            } else if ("This Week".equals(period)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date cutoff = cal.getTime();
                transactions.removeIf(t -> t.getDate().before(cutoff));
            } else if ("This Month".equals(period)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date cutoff = cal.getTime();
                transactions.removeIf(t -> t.getDate().before(cutoff));
            }

        }

        mainFrame.getCardPanel().removeAll();

        if (transactions.isEmpty()) {
            JLabel message = new JLabel("No Transaction Found");
            message.setFont(AppTheme.TITLE_FONT);
            message.setForeground(AppTheme.TEXT_SECONDARY);
            mainFrame.getCardPanel().add(message);
        } else {
            for (Transaction t : transactions) {
                mainFrame.getCardPanel().add(new TransactionCard(t, null, null));
            }

        }
        mainFrame.getCardPanel().revalidate();
        mainFrame.getCardPanel().repaint();
    }

}
