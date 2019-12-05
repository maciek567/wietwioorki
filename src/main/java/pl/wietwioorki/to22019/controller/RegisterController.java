package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterController {

    @FXML
    public TextField registrationUserName;

    @FXML
    public TextField email;

    @FXML
    public PasswordField registrationPassword;

    @FXML
    public PasswordField passwordConfirmation;

    @FXML
    public Button register;

    @FXML
    public void handleRegistration(ActionEvent actionEvent) {
    }
}
