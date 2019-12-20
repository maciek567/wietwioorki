package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Constants;

@Controller
public class LoginController {

    @Autowired
    private Constants constant;

    @FXML
    public TextField userName;

    @FXML
    public PasswordField password;

    @FXML
    public Button login;

    @FXML
    public void handleLogin(ActionEvent actionEvent) {

    }
}
