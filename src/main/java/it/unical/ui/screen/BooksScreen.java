package it.unical.ui.screen;

import it.unical.model.Book;
import it.unical.ui.component.BookSearch;
import it.unical.ui.component.BookTable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BooksScreen extends JPanel {
    public BooksScreen(List<Book> books) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        BookSearch searchPanel = new BookSearch(
            new String[]{"Titolo", "Autore", "ISBN"},
            (field, query) -> {}
        );
        JScrollPane bookTable = new JScrollPane(new BookTable(books));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        add(searchPanel);
        add(bookTable);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}
