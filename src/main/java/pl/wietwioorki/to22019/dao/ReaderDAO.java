package pl.wietwioorki.to22019.dao;

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

    public static Reader findByName(String text) {
        for(Reader reader : readers) {
            if(reader.equals(text)) return reader;
        }
        return null;
    }

}
