package pl.wietwioorki.to22019.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class Author {
    private int authorId;
    private String fullName;
    private List<Book> writtenBooks;

    public void addBook(Book book) { //todo here or in the bookrepository?
        writtenBooks.add(book);
    }
}
