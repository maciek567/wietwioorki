package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.Constants;

import java.util.List;

import static pl.wietwioorki.to22019.util.InfoMessage.pendingReservationsInfoHeader;

public class CredentialsValidator {

    public boolean validateCredentials(Constants constants, String userName, String password) {
        User logUser = UserDAO.findByLogin(userName);
        if (logUser == null || !logUser.checkPassword(password)) {
            return false;
        }

        constants.logUser(logUser);
        System.out.println("You are logged in as " + logUser.getLogin());
//        Reader reader = ReaderDAO.findByUser(logUser);
        Reader reader = ReaderDAO.findByPesel(logUser.getPesel());
        List<Reservation> reservations = ReservationDAO.findByReader(reader);
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
