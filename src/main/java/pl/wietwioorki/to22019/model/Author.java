package pl.wietwioorki.to22019.model;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
public class Author {
    private Long authorId;
    private String fullName;
//    private List<Book> writtenBooks;
}
