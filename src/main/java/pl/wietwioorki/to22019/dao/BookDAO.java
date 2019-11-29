package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;

public class BookDAO {
    public static Book findById(int id) {
        return DataGenerator.generateBook();
    }
}
