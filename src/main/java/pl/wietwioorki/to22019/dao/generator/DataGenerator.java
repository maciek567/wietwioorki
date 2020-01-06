package pl.wietwioorki.to22019.dao.generator;

import pl.wietwioorki.to22019.model.*;

import java.util.*;

public class DataGenerator {
    static Long lastID = 0L;
    public static Long generateId() {
        return ++lastID;
    }

    static Author generateAuthor() {
        return new Author("Malgorzata Musierowicz");
    }

    public static Genre generateGenre() {
        return new Genre("powiesc", "prawie najlepsze ksiazki mojego dziecinstwa");
    }

    public static Book generateBook() {
        return new Book("Szosta klepka", generateAuthor(), new Date(), generateGenre());
    }

    static List<Book> generateBookList() {
        List<Book> writtenBooks = new ArrayList<>();
        writtenBooks.add(generateBook());
        writtenBooks.add(new Book("Opium w rosole", generateAuthor(), new Date(), generateGenre()));
        return writtenBooks;
    }

    public static Reader generateReader() {
        Reader reader = new Reader(1234567890L, "Monika Dziedzic", null);
        HashMap<String, Boolean> notificationSettings = new HashMap<>();
        notificationSettings.put("readyBookNotification", true);
        notificationSettings.put("overdueBookNotification", true);
        notificationSettings.put("newReservationNotification", false);
        notificationSettings.put("borrowedBookNotification", false);
        notificationSettings.put("returnedBookNotification", false);
        User user = new User("MonikaDziedzic", "password", Role.U, "a@a.com", reader,
                0, 0, notificationSettings);
        reader.setUser(user);
        return reader;
    }
}
