package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.EmailUtil;

import java.util.Date;
import java.util.List;

import static pl.wietwioorki.to22019.model.ReservationStatus.ACTIVE;
import static pl.wietwioorki.to22019.util.ErrorMessage.loginErrorHeader;
import static pl.wietwioorki.to22019.util.ErrorMessage.wrongCredentialsErrorContent;

@Controller
public class LoginController extends AbstractWindowController {

    @FXML
    public TextField userName;

    @FXML
    public PasswordField password;

    @FXML
    public Button login;

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        System.out.println("Searching for " + userName.getText());

        if (!sessionConstants.getCredentialsValidator().validateCredentials(sessionConstants, userName.getText(), password.getText())) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, loginErrorHeader, wrongCredentialsErrorContent);
        }
        else {
            if(checkIfAnyBookIsOverdue() &&
                    sessionConstants.getCurrentUser().getNotificationSettings().get(ReservationStatus.OVERDUE)) {
                EmailUtil.handleEmail(sessionConstants, sessionConstants.getCurrentReader());
            }

            // for statistics
            User loggedInUser = sessionConstants.getUserRepository().findByLogin(userName.getText());
            loggedInUser.incrementNoLogins();
            sessionConstants.getUserRepository().save(loggedInUser);
            closeWindowAfterSuccessfulAction(actionEvent);
        }
    }

    private boolean checkIfAnyBookIsOverdue() {
        List<Reservation> reservations = sessionConstants.getReservationRepository().findByReader(sessionConstants.getCurrentReader());
        for(Reservation reservation : reservations) {
            if(reservation.getReservationStatus() == ACTIVE) {
                if (reservation.getReservationEndDate().before(new Date())) {
                    return true;
                }
            }
        }
        return false;
    }
}
