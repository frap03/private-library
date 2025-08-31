package it.unical.ui.component;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.icons.FlatSearchIcon;

public class BookSearch extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> fieldComboBox;

    public BookSearch(String[] searchFields, BiConsumer<String, String> onSearch) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        searchField = createSearchField();
        fieldComboBox = createComboBox(searchFields);

        JPanel container = new JPanel(new BorderLayout(8, 0));
        container.setOpaque(false);
        container.add(searchField, BorderLayout.CENTER);
        container.add(fieldComboBox, BorderLayout.EAST);

        add(container, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { trigger(); }
            @Override public void removeUpdate(DocumentEvent e) { trigger(); }
            @Override public void changedUpdate(DocumentEvent e) { trigger(); }

            private void trigger() {
                onSearch.accept((String) fieldComboBox.getSelectedItem(), searchField.getText());
            }
        });

        fieldComboBox.addActionListener(e ->
            onSearch.accept((String) fieldComboBox.getSelectedItem(), searchField.getText())
        );
    }

    private JTextField createSearchField() {
        JTextField field = new JTextField() {
            private final Icon searchIcon = new FlatSearchIcon();

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

                if (searchIcon != null) {
                    searchIcon.paintIcon(
                        this, g2,
                        10, (getHeight() - searchIcon.getIconHeight()) / 2
                    );
                }

                g2.dispose();
            }

            @Override
            public Insets getInsets() {
                Insets insets = super.getInsets();
                return new Insets(insets.top, 32, insets.bottom, insets.right);
            }
        };

        field.setPreferredSize(new Dimension(300, 36));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(UIManager.getColor("TextField.background"));
        field.setForeground(UIManager.getColor("TextField.foreground"));
        field.setBorder(UIManager.getBorder("TextField.border"));
        return field;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(130, 36));
        combo.setFocusable(false);
        return combo;
    }
}

