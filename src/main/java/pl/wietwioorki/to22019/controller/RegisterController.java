package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Role;
import pl.wietwioorki.to22019.model.User;

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
    public TextField pesel;

    @FXML
    public Button register;

    @FXML
    public void handleRegistration(ActionEvent actionEvent) {
        String login = registrationUserName.getText();
        if(UserDAO.findByLogin(login)!=null){
            System.out.println("User with this login already exist");
            return;
        }

        String password = registrationPassword.getText();
        String passwordConf = passwordConfirmation.getText();

        if(!password.equals(passwordConf)){
            System.out.println("Password and password confirmation are not correct");
            return;
        }

        Long peselNumber = null;
        try {
            peselNumber = Long.parseLong(pesel.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Bad pesel format");
            return;
        }
        if(peselNumber==null || peselNumber<=0){
            System.out.println("Bad pesel");
            return;
        }
        if(UserDAO.findByPesel(peselNumber)!=null){
            System.out.println("User with this pesel already exist");
            return;
        }
        if(ReaderDAO.findByPesel(peselNumber)==null){
            System.out.println("You need to add reader with this pesel");
            return;
        }
        User user = new User(DataGenerator.generateId(), login, password, Role.U, peselNumber);
        UserDAO.addUser(user);
        System.out.println("User added succesfully: ID: " + user.getId());
    }
}
