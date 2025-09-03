package it.unical.ui.component;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BookToolBox extends JPanel {

    public BookToolBox(
        String[] searchFields,
        BiConsumer<String, String> onSearch,
        Runnable onAddClick,
        Runnable onImportClick,
        Runnable onExportClick
    ) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = createSearchField();
        JComboBox<String> fieldComboBox = createComboBox(searchFields);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(fieldComboBox, BorderLayout.EAST);

        JPanel bottomPanel = getBottomPanel(onAddClick, onImportClick, onExportClick);

        add(searchPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

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

    private static JPanel getBottomPanel(Runnable onAddClick, Runnable onImportClick, Runnable onExportClick) {
        JButton importButton = new IconButton("icons/import.svg", "Importa file", onImportClick);
        JButton exportButton = new IconButton("icons/export.svg", "Esporta file", onExportClick);
        JButton addButton = new IconButton("icons/add.svg", "Aggiungi elemento", onAddClick);


        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftButtonPanel.setOpaque(false);
        leftButtonPanel.add(importButton);
        leftButtonPanel.add(exportButton);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightButtonPanel.setOpaque(false);
        rightButtonPanel.add(addButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(leftButtonPanel, BorderLayout.WEST);
        bottomPanel.add(rightButtonPanel, BorderLayout.EAST);
        return bottomPanel;
    }

    private JTextField createSearchField() {
        JTextField field = new SearchField();
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
