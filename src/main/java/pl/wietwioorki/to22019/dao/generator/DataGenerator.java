package pl.wietwioorki.to22019.dao.generator;

import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;
import pl.wietwioorki.to22019.model.Reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DataGenerator {
    static Long generateId() {
        return 23456789L; // todo
    }

    static Author generateAuthor() {
        return new Author(1, "Malgorzata Musierowicz");
    }

    static Genre generateGenre() {
        return new Genre(1, "powiesc", "prawie najlepsze ksiazki mojego dziecinstwa");
    }

    static Book generateBook() {
        return new Book(1L, "Szosta klepka", generateAuthor(), new Date(), generateGenre());
    }

    static List<Book> generateBookList() {
        List<Book> writtenBooks = new ArrayList<>();
        writtenBooks.add(generateBook());
        writtenBooks.add(new Book(2L, "Opium w rosole", generateAuthor(), new Date(), generateGenre()));
        return writtenBooks;
    }

    static Reader generateReader() {
        return new Reader(1234567890, "Monika Dziedzic", new Date());
    }
}
