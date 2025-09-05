package it.unical.ui.screen;

import it.unical.BookRepository;
import it.unical.model.Book;
import it.unical.ui.component.BookToolBox;
import it.unical.ui.component.BookTable;
import it.unical.ui.component.UpdateBookDialog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class BooksScreen extends JPanel {
    public BooksScreen(List<Book> books) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        BookToolBox searchPanel = new BookToolBox(
            new String[]{"Titolo", "Autore", "ISBN"},
            (field, query) -> {},
            () -> {
                UpdateBookDialog updateBookDialog = UpdateBookDialog.insert(System.out::println);
                updateBookDialog.setVisible(true);
            },
            () -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("File JSON", "json"));
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    BookRepository.getInstance().importFile(file);
                    System.out.println(file.getAbsolutePath());
                }
            },
            () -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Esporta JSON");
                int result = chooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    String json = BookRepository.getInstance().getJsonBooks();
                    try { Files.writeString(selectedFile.toPath(), json); }
                    catch (IOException e) { throw new RuntimeException(e); }
                }

            }
        );

        JScrollPane bookTable = new JScrollPane(
            new BookTable(
                books,
                (row, book) -> {
                    UpdateBookDialog dialog = new UpdateBookDialog(
                            book,
                            (newBook) -> {},
                            () -> {}
                    );
                    dialog.setVisible(true);
                }
            )
        );

        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        add(searchPanel);
        add(bookTable);
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 10, 0, 10);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}
