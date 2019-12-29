package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.Constants;

import java.util.List;
import java.util.Optional;

import static pl.wietwioorki.to22019.util.InfoMessage.pendingReservationsInfoHeader;

@Component
public class CredentialsValidator extends MyValidator {

    public boolean validateCredentials(Constants constants, String userName, String password) {
        User logUser = userRepository.findByLogin(userName);
        if (logUser == null || !logUser.checkPassword(password)) {
            return false;
        }

        constants.logUser(logUser);
        System.out.println("You are logged in as " + logUser.getLogin());
//        Reader reader = ReaderDAO.findByUser(logUser);

        Optional<Reader> reader = readerRepository.findById(logUser.getPesel());
        if (reader.isEmpty()) {
            return false;
        }

        List<Reservation> reservations = reservationRepository.findByReader(reader.get());
        if (reservations.size() > 0) {
            StringBuilder contentText = new StringBuilder();
            for (Reservation reservation : reservations) {
                contentText.append(reservation.getBooksTitleProperty());
                contentText.append("\n");
            }
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, pendingReservationsInfoHeader, contentText.toString());
        }
        return true;
    }
}
