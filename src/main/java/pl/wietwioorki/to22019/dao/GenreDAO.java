package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreDAO { //todo: fix DAO objects
    private static List<Genre> genres = new ArrayList<>();

    public static Genre findByName(String name) {
        for (Genre genre: genres) {
            if(genre.getName().equals(name)){
                return genre;
            }
        }
        return null;
    }
    public static Genre findById(Long id) {
        return DataGenerator.generateGenre();
    }
}
