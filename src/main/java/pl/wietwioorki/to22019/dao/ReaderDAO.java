package pl.wietwioorki.to22019.dao;

import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.User;

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
            if(reader.getFullName().equals(text)) return reader;
        }
        return null;
    }

    public static Reader findByPesel(Long pesel) {
        for(Reader reader : readers) {
            if(reader.getPesel().equals(pesel)) return reader;
        }
        return null;
    }

    public static Reader findByUser(User user){
        for(Reader reader : readers){
            if(reader.getUser().equals(user)) return reader;
        }
        return null;
    }

    public static List<Reader> getReaders() {
        return readers;
    }
}
