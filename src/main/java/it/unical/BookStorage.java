package it.unical;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unical.model.Book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public final class BookStorage {
    private static final BookStorage INSTANCE = new BookStorage();
    private final File file;

    private BookStorage() {
        String path = System.getProperty("user.home") + "/PrivateLibrary/books.json";
        file = Path.of(path).toFile();
    }

    public List<Book> read() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file: " + file);
                }
                Files.writeString(file.toPath(), "[]");
            }
            String json = Files.readString(file.toPath());
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());
        }
        catch (IOException e) {
            System.err.println("Unable to read file: " + file);
            throw new RuntimeException(e);
        }
        catch (JsonSyntaxException e) {
            System.err.println("File is corrupted reset...");
            clear();
            return List.of();
        }
    }

    public void save(List<Book> book) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(book);
            Files.writeString(file.toPath(), json);
        } catch (IOException e) {
            System.err.println("Unable to write to file: " + file);
            e.printStackTrace();
        }
    }

    public static BookStorage getInstance() {
        return INSTANCE;
    }

    private void clear() {
        try { Files.writeString(file.toPath(), "[]"); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
