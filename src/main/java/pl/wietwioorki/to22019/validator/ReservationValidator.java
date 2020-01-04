package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.util.List;
import java.util.Optional;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Getter
@Component
public class ReservationValidator extends MyValidator {
    private String specificErrorHeader = "adding reservation";
    private Reader reader;
    private Book book;

    @Autowired
    PeselValidator peselValidator;

    public boolean validatePesel(String pesel) {
        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            Optional<Reader> optionalReader = readerRepository.findById(Long.parseLong(pesel));
            if (optionalReader.isEmpty()) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselDoesNotExistErrorContent);
                return false;
            }
            reader = optionalReader.get();
            return true;
        }
        return false;
    }

    public boolean validateBook(String bookTitle) {
        if (bookTitle.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "Book title" + emptyFieldErrorContent);
            return false;
        }

        book = bookRepository.findByTitle(bookTitle);
        if (book == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, bookWithGivenTitleDoesNotExistErrorContent);
            return false;
        }
        return true;
    }

    public boolean validateReservation(Book book) {
        List<Reservation> reservations = reservationRepository.findByBook(book);
        if (reservations.size() == 1) { // then it's already reserved and reader cannot borrow this book. We don't have to
            // check the reservation status because when book is returned, then reservation is moved to CompleteReservations
        //todo: == availableBookItems // it won't be >=
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, noMoreBooksAvailableErrorContent);
            return false;
        }
        return true;
    }
}
