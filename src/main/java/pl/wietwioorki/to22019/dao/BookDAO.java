package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static List<Book> books = new ArrayList<Book>();

    public static Book findByTitle(String title) {
        for (Book book: books) {
            if(book.getTitle().equals(title)){
                return book;
            }
        }
        return null;
    }

    public static void addBook(Book book){
        books.add(book);
    }

    public List<Book> getAllBooks(){
        return books;
    }
    public static Book findById(Long id) {
        return DataGenerator.generateBook();
    }
}
