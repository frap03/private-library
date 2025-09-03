package it.unical.ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {
    public IconButton(String svgPath, String tooltip, Runnable onClick) {
        super(new FlatSVGIcon(svgPath, 20, 20));
        setToolTipText(tooltip);
        setFocusable(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(40, 36));
        addActionListener(e -> onClick.run());
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        setBackground(UIManager.getColor("Component.background"));
        setOpaque(true);
        setContentAreaFilled(true);
    }
}
