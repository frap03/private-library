package it.unical.ui.screen;

import com.google.gson.Gson;
import it.unical.BookListener;
import it.unical.BookPublisher;
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

public class BooksScreen extends JPanel implements BookListener {
    private final BookPublisher publisher = new BookPublisher();
    private String rawJsonBook;

    BookTable table = new BookTable(this::onUpdateClick);

    BookToolBox toolBox = new BookToolBox(
        new String[]{"Titolo", "Autore", "ISBN"},
        this::onSearch, this::onAddClick,
        this::onImportClick, this::onExportClick
    );


    public BooksScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        publisher.subscribe(this);
        publisher.subscribe(table);
        toolBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        add(toolBox);
        add(new JScrollPane(table));
    }

    public void onSearch(String field, String query) {

    }

    public void onAddClick() {
        UpdateBookDialog updateBookDialog = UpdateBookDialog.insert(System.out::println);
        updateBookDialog.setVisible(true);
    }

    public void onUpdateClick(int row, Book book) {
        UpdateBookDialog dialog = new UpdateBookDialog(
            book,
            (newBook) -> {},
            () -> {}
        );
        dialog.setVisible(true);
    }

    public void onImportClick() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("File JSON", "json"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            publisher.publish(file);
            System.out.println(file.getAbsolutePath());
        }
    }

    public void onExportClick() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Esporta JSON");
        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try { Files.writeString(selectedFile.toPath(), rawJsonBook); }
            catch (IOException e) { throw new RuntimeException(e); }
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 10, 0, 10);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    @Override
    public void update(List<Book> book) {
        Gson gson = new Gson();
        rawJsonBook = gson.toJson(book);
    }
}
