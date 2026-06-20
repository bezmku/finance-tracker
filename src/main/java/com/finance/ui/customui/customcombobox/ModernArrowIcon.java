package com.finance.ui.customui.customcombobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class ModernArrowIcon implements Icon {

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.DARK_GRAY);

        int[] xPoints = { x + 2, x + 10, x + 6 };
        int[] yPoints = { y + 4, y + 4, y + 9 };
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.dispose();
    }

    @Override
    public int getIconHeight() {
        return 12;
    }

    @Override
    public int getIconWidth() {
        return 12;
    }

}
