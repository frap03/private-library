package it.unical.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    String isbn;
    String title;
    String author;
    ReadingStatus status;
    Genre genre;
    Integer rating;
}
