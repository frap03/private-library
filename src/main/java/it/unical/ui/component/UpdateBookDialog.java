package it.unical.ui.component;

import it.unical.model.Book;
import it.unical.model.Genre;
import it.unical.model.ReadingStatus;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class UpdateBookDialog extends JDialog {
    private final JTextField isbnField = new JTextField(20);
    private final JTextField titleField = new JTextField(20);
    private final JTextField authorField = new JTextField(20);
    private final JComboBox<ReadingStatus> statusBox = new JComboBox<>(ReadingStatus.values());
    private final JComboBox<Genre> genreBox = new JComboBox<>(Genre.values());
    private final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
    private final JButton confirmBtn = new IconButton("icons/check.svg", "Conferma", this::handleConfirm);
    private final JButton cancelBtn = new IconButton("icons/close.svg", "Cancella", this::dispose);
    private final JButton deleteBtn = new IconButton("icons/delete.svg", "Elimina", this::handleDelete);

    private final Book book;
    private final Consumer<Book> onConfirm;
    private final Runnable onDelete;

    public UpdateBookDialog(Book book, Consumer<Book> onConfirm, Runnable onDelete) {
        super((Frame) null, true);
        this.book = book;
        this.onConfirm = onConfirm;
        this.onDelete = onDelete;
        deleteBtn.setEnabled(this.onDelete != null);

        setTitle(book == null ? "Nuovo Libro" : "Modifica Libro");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);

        formPanel.add(new JLabel("Titolo:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Autore:"));
        formPanel.add(authorField);

        formPanel.add(new JLabel("Stato lettura:"));
        formPanel.add(statusBox);

        formPanel.add(new JLabel("Genere:"));
        formPanel.add(genreBox);

        formPanel.add(new JLabel("Valutazione (1-5):"));
        formPanel.add(ratingSpinner);

        add(formPanel, BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
        DocumentListener validationListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { checkValidBook(); }
            @Override public void removeUpdate(DocumentEvent e) { checkValidBook(); }
            @Override public void changedUpdate(DocumentEvent e) { checkValidBook(); }
        };

        isbnField.getDocument().addDocumentListener(validationListener);
        titleField.getDocument().addDocumentListener(validationListener);
        authorField.getDocument().addDocumentListener(validationListener);

        if (book != null) {
            isbnField.setText(book.getIsbn());
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            statusBox.setSelectedItem(book.getStatus());
            genreBox.setSelectedItem(book.getGenre());
            ratingSpinner.setValue(book.getRating());
        }

        checkValidBook();
        pack();
        setLocationRelativeTo(null);
    }

    public static UpdateBookDialog insert(Consumer<Book> onConfirm) {
        return new UpdateBookDialog(null, onConfirm, null);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(leftPanel, BorderLayout.WEST);
        buttonsPanel.add(rightPanel, BorderLayout.EAST);
        leftPanel.add(confirmBtn);
        leftPanel.add(cancelBtn);
        rightPanel.add(deleteBtn);
        return buttonsPanel;
    }

    private void handleConfirm() {
        onConfirm.accept(book);
        dispose();
    }

    private void handleDelete() {
        onDelete.run();
        dispose();
    }

    private void checkValidBook() {
        boolean isValid = !isbnField.getText().trim().isEmpty()
            && !titleField.getText().trim().isEmpty()
            && !authorField.getText().trim().isEmpty()
            && statusBox.getSelectedItem() != null
            && genreBox.getSelectedItem() != null
            && ratingSpinner.getValue() != null;

        confirmBtn.setEnabled(isValid);
    }
}
