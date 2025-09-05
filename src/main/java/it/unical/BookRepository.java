package it.unical;

import com.google.gson.Gson;
import it.unical.model.Book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;

public final class BookRepository {
    private static final BookRepository INSTANCE = new BookRepository();
    private final String filePath;
    @Getter private List<Book> books;

    private BookRepository() {
        String home = System.getProperty("user.home");
        filePath = home + "/PrivateLibrary/books.json";

        Gson gson = new Gson();
        Path path = Path.of(filePath);
        File file = path.toFile();
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file: " + file);
                }
                Files.writeString(path, "[]");
            }
            String json = Files.readString(path);
            books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void save(Book book) {
        books.add(book);
    }

    public void remove(Book book) {
        books = books.stream().filter(b -> !Objects.equals(b.getIsbn(), book.getIsbn()))
            .collect(Collectors.toList());
    }

    public void importFile(File file) {
        Gson gson = new Gson();
        try {
            String json =  Files.readString(file.toPath());
            books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());
            System.out.println("Il file è valido: scrittura su disco....");
            Files.writeString(file.toPath(), json);
            System.out.println("File importato con successo");
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public String getJsonBooks() {
        Gson gson = new Gson();
        return gson.toJson(books);
    }

    public static BookRepository getInstance() {
        return INSTANCE;
    }
}
