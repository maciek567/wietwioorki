package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Book {
    private Long bookId;
    private String title;
    private Author author;
    private Date publicationDate;
    private Genre genre;
}
