package it.unical;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Private Library");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setResizable(false);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            frame.setVisible(true);
        });
    }
}
