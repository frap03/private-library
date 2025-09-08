package it.unical;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import it.unical.model.Book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class BookPublisher {
    private final List<BookListener> subscribers = new ArrayList<>();
    private final BookStorage storage = BookStorage.getInstance();
    private final Gson gson = new Gson();
    private final List<Book> books = storage.read();

    public void subscribe(BookListener subscriber) {
        subscribers.add(subscriber);
        subscriber.update(Collections.unmodifiableList(books));
    }

    public void unsubscribe(BookListener subscriber) {
        subscribers.remove(subscriber);
    }

    public void add(Book book) {
        this.books.removeIf(b -> b.getIsbn().equals(book.getIsbn()));
        this.books.add(book);
        this.storage.save(books);
        notifyListeners();
    }

    public void remove(Book book) {
        this.books.removeIf(b -> b.getIsbn().equals(book.getIsbn()));
        this.storage.save(books);
        notifyListeners();
    }

    public void filter(Predicate<Book> filter) {
        notifyListeners(this.books.stream().filter(filter).toList());
    }

    public void importFile(File file) {
        try {
            String json = Files.readString(file.toPath());
            List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());
            this.books.clear();
            this.books.addAll(books);
            storage.save(books);
            notifyListeners();
        }
        catch (JsonSyntaxException e) {
            System.err.println("File to import is not valid List<Book>");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.err.println("Could not open file: " + file.getAbsolutePath());
        }
    }

    private void notifyListeners() {
        notifyListeners(books);
    }

    private void notifyListeners(List<Book> books) {
        for (BookListener subscriber : subscribers) {
            subscriber.update(Collections.unmodifiableList(books));
        }
    }
}
