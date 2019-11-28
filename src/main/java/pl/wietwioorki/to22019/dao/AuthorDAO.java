package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;

import java.util.List;

public class AuthorDAO {
    //    public static Author findByName(String name) { // fixme - I think it's not necessary, because we should return id of author object chosen from combobox instead of simply name (better time complexity)

//        int id = 1; //persistencyLayer.find(...)
//        return DataGenerator.generateAuthor();
//    }
    public List<Book> findAllBooks(int id) { //todo
        return DataGenerator.generateBookList();
    }

    public static Author findById(int id) {
        return DataGenerator.generateAuthor();
    }
}
