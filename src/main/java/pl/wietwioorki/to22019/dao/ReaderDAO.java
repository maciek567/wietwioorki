package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Reader;

public class ReaderDAO {
    public static Reader findById(int id) {
        return DataGenerator.generateReader();
    }
}
