package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Genre;

public class GenreDAO {
    public static Genre findById(int id) {
        return DataGenerator.generateGenre();
    }
}
