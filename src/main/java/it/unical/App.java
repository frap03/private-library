package it.unical;

import com.formdev.flatlaf.FlatLightLaf;
import it.unical.ui.screen.BooksScreen;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new FlatLightLaf());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Private Library");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setMinimumSize(new Dimension(800, 600));
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            frame.setLayout(new BorderLayout());
            frame.add(new BooksScreen(), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
