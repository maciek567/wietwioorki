package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.model.User;

@Controller
public class LoginController extends AbstractWindowController{

    @FXML
    public TextField userName;

    @FXML
    public PasswordField password;

    @FXML
    public Button login;

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        User user = UserDAO.tryLogin(userName.getText(), password.getText());
        if(user == null){
            System.out.println("bad password or login");
        }
        else{
            constants.setActualUser(user);
            System.out.println("logged in as: " + constants.getActualUser().getLogin());
        }
    }
}
