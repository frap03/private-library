package it.unical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import it.unical.model.Book;
import it.unical.model.Genre;
import it.unical.model.ReadingStatus;

public class BookStorageTest {
    private File tempFile;
    private BookStorage storage;

    private final List<Book> testData = List.of(
        new Book("978-1234567890", "The Haunted House", "Author One", ReadingStatus.NOT_READ, Genre.HORROR, 3),
        new Book("978-0987654321", "Mystery Island", "Author Two", ReadingStatus.READING, Genre.MYSTERY, 4)
    );


    @BeforeEach
    void setUp() throws Exception {
        tempFile = File.createTempFile("books", ".json");
        Files.writeString(tempFile.toPath(), "[]");
        storage = BookStorage.getInstance();

        Field fileField = BookStorage.class.getDeclaredField("file");
        fileField.setAccessible(true);
        fileField.set(storage, tempFile);
        fileField.setAccessible(false);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void readShouldReturnEmptyListWhenFileIsEmpty() {
        List<Book> books = storage.read();
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void readShouldReturnEmptyListWhenJsonIsCorrupted() throws IOException {
        Files.writeString(tempFile.toPath(), "{ bad json ]");
        List<Book> books = storage.read();
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }


    @Test
    void readShouldCreateFileWhenItDoesNotExist() throws Exception {
        assertTrue(tempFile.delete());

        List<Book> books = storage.read();
        assertTrue(tempFile.exists());
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void readShouldReturnCorrectBooksWhenFileContainsMultipleBooks() throws IOException {
        Files.writeString(tempFile.toPath(), new Gson().toJson(testData));

        List<Book> result = storage.read();
        assertEquals(2, result.size());

        Book first = result.get(0);
        assertEquals("The Haunted House", first.getTitle());
        assertEquals(ReadingStatus.NOT_READ, first.getStatus());
        assertEquals(Genre.HORROR, first.getGenre());

        Book second = result.get(1);
        assertEquals("Mystery Island", second.getTitle());
        assertEquals(ReadingStatus.READING, second.getStatus());
        assertEquals(Genre.MYSTERY, second.getGenre());
    }

    @Test
    void saveShouldPersistBooksAndReadShouldReturnExactSameBooks() {
        storage.save(testData);
        List<Book> loadedBooks = storage.read();
        assertEquals(testData, loadedBooks);
    }



    @Test
    void saveShouldOverwriteFileWhenCalledMultipleTimes() {
        storage.save(testData);

        List<Book> secondList = List.of(
            new Book("ISBN-333", "Third Book", "Author C", ReadingStatus.READING, Genre.FANTASY, 4)
        );
        storage.save(secondList);

        List<Book> loadedBooks = storage.read();
        assertEquals(1, loadedBooks.size());
        assertEquals("Third Book", loadedBooks.get(0).getTitle());
        assertEquals(secondList, loadedBooks);
    }
}
