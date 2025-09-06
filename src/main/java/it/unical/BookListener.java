package it.unical;

import it.unical.model.Book;

import java.util.List;

public interface BookListener {
    void update(List<Book> book);
}
