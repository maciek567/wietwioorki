package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String bookTitle);

    List<Book> findAllByTitle(String bookTitle);

    List<Book> findBooksByAuthor(Author author);
    List<Book> findBooksByGenre(Genre genre);
}