package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    private static List<Reader> readers = new ArrayList<Reader>();

    public static Reader findByFullName(String name) {
        for (Reader reader: readers) {
            if(reader.getFullName().equals(name)){
                return reader;
            }
        }
        return null;
    }

    public static void addReader(Reader reader){
        readers.add(reader);
    }

    public List<Reader> getAllReaders(){
        return readers;
    }

    public static Reader findById(int id) {
        return DataGenerator.generateReader();
    }
}
