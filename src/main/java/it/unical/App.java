package it.unical;

import it.unical.model.Book;
import it.unical.model.Genre;
import it.unical.model.ReadingStatus;
import it.unical.ui.component.BookTable;

import javax.swing.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Private Library");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setResizable(false);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JScrollPane scrollPane = new JScrollPane(new BookTable(getMockBooks()));
            frame.add(scrollPane);

            frame.setVisible(true);
        });
    }

    private static List<Book> getMockBooks() {
        return List.of(
            new Book("978-0134685991", "Effective Java", "Joshua Bloch", ReadingStatus.READ, Genre.HORROR, 5),
            new Book("978-0321356680", "Java Concurrency in Practice", "Brian Goetz", ReadingStatus.READ, Genre.HORROR,4),
            new Book("978-1491917660", "Java: The Complete Reference", "Herbert Schildt", ReadingStatus.READ, Genre.HORROR, 4)
        );
    }
}
