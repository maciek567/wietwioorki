package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    private static List<Genre> genres = new ArrayList<Genre>();

    public static Genre findByName(String name) {
        for (Genre genre: genres) {
            if(genre.getName().equals(name)){
                return genre;
            }
        }
        return new Genre(DataGenerator.generateId(), name, "Add default");
    }
    public static Genre findById(Long id) {
        return DataGenerator.generateGenre();
    }
}
