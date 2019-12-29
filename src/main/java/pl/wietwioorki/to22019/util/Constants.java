package pl.wietwioorki.to22019.util;

import javafx.scene.control.DatePicker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.repository.BookRepository;
import pl.wietwioorki.to22019.validator.BookValidator;
import pl.wietwioorki.to22019.validator.ReaderValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Getter
@Setter
public class Constants {
    @Autowired
    ReaderValidator readerValidator;
    @Autowired
    BookValidator bookValidator;
//    @Autowired
//    AuthorValidator authorValidator;
//    @Autowired
//    GenreValidator genreValidator;

    //    @Autowired todo: add more repos
    @Autowired
    BookRepository bookRepository;

    User currentUser = null;

    public void logUser(User user){
        currentUser = user;
    }

    public String getUserLogin(){
        String name = "";
        if(currentUser != null){
            name = currentUser.getLogin();
        }
        return name;
    }

    public Optional<Reader> getCurrentReader(){
        Optional<Reader> reader = Optional.empty();
        if(currentUser != null){
            reader = currentUser.getReader();
        }
        return reader;
    }

    public Date datePickerConverter(DatePicker datePicker) throws ParseException, DateTimeException {
        // workaround to enable manual (using keyboard) date changing (from https://bugs.openjdk.java.net/browse/JDK-8144499)
        DatePicker newDatePicker = new DatePicker(datePicker.getConverter().fromString(datePicker.getEditor().getText()));

        String formatted = newDatePicker.getValue().format((DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(formatted);
    }

    public Book getReservedBook(String bookTitle) {
        List<Book> books = bookRepository.findAllByTitle(bookTitle);
        Book reservedBook = books.get(0);
        int smallestQueueSize = books.get(0).getReaderQueueSize();

        for (Book book : books) {
            if (book.isReaderQueueEmpty()) {
                reservedBook = book;
                break;
            }
            if (book.getReaderQueueSize() < smallestQueueSize) {
                smallestQueueSize = book.getReaderQueueSize();
                reservedBook = book;
            }
        }
        return reservedBook;
    }
}
