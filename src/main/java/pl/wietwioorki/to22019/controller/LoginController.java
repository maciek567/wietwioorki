package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.SessionConfig;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.User;

import java.util.List;

@Controller
public class LoginController {

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
        if(logUser != null){
            SessionConfig.logUser(logUser);
            System.out.println("You are logged in as " + logUser.getLogin());
            Reader reader = ReaderDAO.findByUser(logUser);
            List<Reservation> reservations = ReservationDAO.findByReader(reader);
            if(reservations.size() > 0){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("You have pending reservations");
                StringBuilder contentText = new StringBuilder();
                for(Reservation reservation: reservations){
                    contentText.append(reservation.getBooksTittleProperty());
                    contentText.append("\n");
                }
                a.setContentText(contentText.toString());
                a.show();
            }
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Login error");
            a.setContentText("Wrong credentials given");
            a.show();
        }

    }
}
