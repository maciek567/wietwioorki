package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;

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
            closeWindowAfterSuccessfulAction(actionEvent);
        }

        User loggedInUser = sessionConstants.getUserRepository().findByLogin(userName.getText());
        loggedInUser.incrementNoLogins();
        sessionConstants.getUserRepository().save(loggedInUser);
    }
}
