package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import lombok.Getter;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Getter
public class ReservationValidator {
    private String specificErrorHeader = "adding reservation";
    private Reader reader;

    public boolean validatePesel(String pesel) {
        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            if (ReaderDAO.findByPesel(Long.parseLong(pesel)) == null) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselDoesNotExistErrorContent);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean validateReader(String pesel) {
        reader = ReaderDAO.findByPesel(Long.parseLong(pesel));

        if (reader == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselDoesNotExistErrorContent);
            return false;
        }
        return true;
    }

    public boolean validateBook(String bookTitle) {
        if (bookTitle.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "Book title" + emptyFieldErrorContent);
            return false;
        }

        Book book = BookDAO.findOneByTitle(bookTitle);
        if (book == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, bookWithGivenTitleDoesNotExistErrorContent);
            return false;
        }
        return true;
    }
}
