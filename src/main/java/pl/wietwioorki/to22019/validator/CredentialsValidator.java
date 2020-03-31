package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.Notification;
import pl.wietwioorki.to22019.util.SessionConstants;

import java.util.List;
import java.util.Optional;

import static pl.wietwioorki.to22019.util.InfoMessage.pendingReservationsInfoHeader;

@Component
public class CredentialsValidator extends MyValidator {

    public boolean validateCredentials(SessionConstants sessionConstants, String userName, String password) {
        User logUser = sessionConstants.getUserRepository().findByLogin(userName);
        if (logUser == null || !logUser.checkPassword(password)) {
            return false;
        }

        sessionConstants.logUser(logUser);
        System.out.println("You are logged in as " + logUser.getLogin());

        Optional<Reader> reader = sessionConstants.getReaderRepository().findById(logUser.getReader().getPesel());
        if (reader.isEmpty()) {
            return false;
        }

        List<Reservation> reservations = sessionConstants.getReservationRepository().findByReader(reader.get());
        if (reservations.size() > 0) {
            String contentText = Notification.formNotification(reservations);
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, pendingReservationsInfoHeader, contentText);
        }
        return true;
    }

    public boolean validateLogin(SessionConstants sessionConstants, String userName){
        User logUser = sessionConstants.getUserRepository().findByLogin(userName);
        return logUser != null;
    }
}
