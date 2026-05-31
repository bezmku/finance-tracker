package com.finance.ui.customui.customcombobox;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

public class CustomComoboBoxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setBorder(new EmptyBorder(8, 10, 8, 10));

        if (index == -1) {
            setBorder(new EmptyBorder(2, 5, 2, 5));
            setOpaque(false);
            setForeground(Color.DARK_GRAY);
        } else {
            setBorder(new EmptyBorder(8, 10, 8, 10));
            setOpaque(true);
            if (isSelected) {
                setBackground(new Color(240, 240, 245));
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.DARK_GRAY);
            }

        }
        return c;

    }

}
