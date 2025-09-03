package it.unical.ui.screen;

import it.unical.model.Book;
import it.unical.ui.component.BookToolBox;
import it.unical.ui.component.BookTable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BooksScreen extends JPanel {
    public BooksScreen(List<Book> books) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        BookToolBox searchPanel = new BookToolBox(
            new String[]{"Titolo", "Autore", "ISBN"},
            (field, query) -> {},
            () -> System.out.println("Cliccato aggungi libro"),
            () -> System.out.println("Cliccato import libri"),
            () -> System.out.println("Cliccato export libri")
        );

        JScrollPane bookTable = new JScrollPane(
            new BookTable(
                books,
                (row, book) -> System.out.println("#" + row + " " + book)
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
