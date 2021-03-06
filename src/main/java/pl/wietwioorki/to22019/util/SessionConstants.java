package pl.wietwioorki.to22019.util;

import javafx.scene.control.DatePicker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.repository.*;
import pl.wietwioorki.to22019.validator.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Getter
@Setter
public class SessionConstants {
    // validators
    @Autowired
    BookBorrowValidator bookBorrowValidator;

    @Autowired
    BookValidator bookValidator;

    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    PeselValidator peselValidator;

    @Autowired
    RegistrationValidator registrationValidator;

    @Autowired
    ReservationValidator reservationValidator;


    // repos
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CompleteReservationRepository completeReservationRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    FineRepository fineRepository;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    User currentUser = null;

    public void logUser(User user) {
        currentUser = user;
        events.userChanged();
    }

    public void logoutUser() {
        currentUser = null;
        events.userChanged();
    }

    public String getUserLogin() {
        String name = null;
        if (currentUser != null) {
            name = currentUser.getLogin();
        }
        return name;
    }

    public Reader getCurrentReader() {
        Reader reader = null;
        if (currentUser != null) {
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

    public MyEventHandler events = new MyEventHandler();
}
