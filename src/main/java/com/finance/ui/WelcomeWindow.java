package com.finance.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.finance.theme.AppTheme;

public class WelcomeWindow extends JFrame {

    public WelcomeWindow() {
        setTitle("WelcomeWindow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setSize(800, 800);
        setLocationRelativeTo(null);

        JPanel header = new JPanel();
        header.setBackground(AppTheme.BG_COLOR);
        JLabel headerLabel = new JLabel("Welcome to Finance Tracker");
        headerLabel.setForeground(AppTheme.TEXT_COLOR);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        headerLabel.setBackground(AppTheme.PANEL_BG);
        header.add(headerLabel, BorderLayout.NORTH);

        JLabel info = new JLabel("""
                <html><div style='text-align:center; width:500px;'>
                Welcome to the Finance Tracker application!<br><br>
                This application is a simple finance tracker application
                designed to help users track their expenses and incomes.
                </div></html>
                """);

        info.setHorizontalAlignment(JLabel.CENTER);
        info.setOpaque(true);
        info.setForeground(Color.WHITE);
        info.setBackground(Color.decode("#3b2579"));
        info.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(info, BorderLayout.CENTER);
        infoPanel.setBackground(AppTheme.BG_COLOR);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(500, 100));
        JButton button = new JButton("Start Tracking");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#3b2579"));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusable(false);

        button.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            new MainFrame();
            dispose();
        })

        );

        buttonPanel.add(button);
        infoPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomeWindow();
    }
}
