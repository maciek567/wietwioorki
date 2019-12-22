package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.util.List;

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
        User logUser = UserDAO.findByLogin(userName.getText());
        if (logUser != null) {
            constants.logUser(logUser);
            System.out.println("You are logged in as " + logUser.getLogin());
            Reader reader = ReaderDAO.findByUser(logUser);
            List<Reservation> reservations = ReservationDAO.findByReader(reader);
            if (reservations.size() > 0) {
                StringBuilder contentText = new StringBuilder();
                for (Reservation reservation : reservations) {
                    contentText.append(reservation.getBooksTittleProperty());
                    contentText.append("\n");
                }
                AlertFactory.showAlert(Alert.AlertType.INFORMATION, "You have pending reservations", contentText.toString());
            }
        } else {
            AlertFactory.showAlert(Alert.AlertType.ERROR, "Login error", "Wrong credentials given");
        }
    }
}
