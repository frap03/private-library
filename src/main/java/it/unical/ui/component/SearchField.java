package it.unical.ui.component;

import com.formdev.flatlaf.icons.FlatSearchIcon;

import javax.swing.*;
import java.awt.*;

public class SearchField extends JTextField {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString("Cerca...", 36, getHeight() / 2 + 5);
        }

        Icon searchIcon = new FlatSearchIcon();
        searchIcon.paintIcon(
            this, g2,
            10, (getHeight() - searchIcon.getIconHeight()) / 2
        );

        g2.dispose();
    }

    @Override
    public Insets getInsets() {
        Insets insets = super.getInsets();
        return new Insets(insets.top, 32, insets.bottom, insets.right);
    }
}
