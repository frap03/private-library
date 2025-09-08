package it.unical.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    String isbn;
    String title;
    String author;
    ReadingStatus status;
    Genre genre;
    Integer rating;
}
