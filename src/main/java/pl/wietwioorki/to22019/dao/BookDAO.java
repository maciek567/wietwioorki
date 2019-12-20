package pl.wietwioorki.to22019.dao;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
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
        System.out.println(book);
    }

    public List<Book> getAllBooks(){
        return books;
    }

    public static ObservableList<Book> getBooksObservable(){
        return new ObservableListBase<Book>() {
            @Override
            public Book get(int index) {
                return books.get(index);
            }

            @Override
            public int size() {
                return books.size();
            }
        };
    }
    public static Book findById(String id) {
        return DataGenerator.generateBook();
    }

    public static Book findById(Long id) {
        for (Book book: books) {
            if(book.getBookId().equals(id)){
                return book;
            }
        }
        return null;
    }
}
