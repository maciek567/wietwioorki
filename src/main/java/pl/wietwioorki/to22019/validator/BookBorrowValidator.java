package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.util.Optional;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Getter
@Component
public class BookBorrowValidator extends MyValidator {
    private String specificErrorHeader = "borrowing book";
    private Reservation reservation;

    public boolean validateReservation(String reservationId) {
        if (reservationId.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "Id" + emptyFieldErrorContent);
            return false;
        }
        if (!StringUtils.isNumeric(reservationId)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongIdErrorContent);
            return false;
        }

        Optional<Reservation> optionalReservation = sessionConstants.getReservationRepository().findById(Long.parseLong(reservationId));

        if (optionalReservation.isEmpty()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, reservationWithGivenIdDoesNotExistErrorContent);
            return false;
        }
        reservation = optionalReservation.get();

        return true;
    }

    public boolean validatePesel(String pesel) {
        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            if (sessionConstants.getReaderRepository().findById(Long.parseLong(pesel)).isEmpty()) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselDoesNotExistErrorContent);
                return false;
            }
            return true;
        }
        return false;
    }
}
