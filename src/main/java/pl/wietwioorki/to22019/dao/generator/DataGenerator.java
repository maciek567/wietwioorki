package pl.wietwioorki.to22019.dao.generator;

import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;
import pl.wietwioorki.to22019.model.Reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataGenerator {
    static Long lastID = 0L;
    public static Long generateId() {
        return ++lastID;
    }

    static Author generateAuthor() {
        return new Author(1L, "Malgorzata Musierowicz");
    }

    public static Genre generateGenre() {
        return new Genre(1L, "powiesc", "prawie najlepsze ksiazki mojego dziecinstwa");
    }

    public static Book generateBook() {
        return new Book(1L, "Szosta klepka", generateAuthor(), new Date(), generateGenre());
    }

    static List<Book> generateBookList() {
        List<Book> writtenBooks = new ArrayList<>();
        writtenBooks.add(generateBook());
        writtenBooks.add(new Book(2L, "Opium w rosole", generateAuthor(), new Date(), generateGenre()));
        return writtenBooks;
    }

    public static Reader generateReader() {
        return new Reader(1234567890L, "Monika Dziedzic", new Date());
    }
}
