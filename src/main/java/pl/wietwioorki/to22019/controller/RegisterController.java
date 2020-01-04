package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Role;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.repository.ReaderRepository;
import pl.wietwioorki.to22019.repository.UserRepository;

import java.util.Optional;

@Controller
public class RegisterController extends AbstractWindowController {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    UserRepository userRepository;

    @FXML
    public TextField name;

    @FXML
    public TextField pesel;

    @FXML
    public TextField login;

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
        String login = this.login.getText();
        if (userRepository.findByLogin(login) != null) {
            System.out.println("User with this login already exist");
            return;
        }

        String password = registrationPassword.getText();
        String passwordConf = passwordConfirmation.getText();

        if (!password.equals(passwordConf)) {
            System.out.println("Password and password confirmation are not correct");
            return;
        }

        long peselNumber;
        try {
            peselNumber = Long.parseLong(pesel.getText());
        } catch (NumberFormatException e) {
            System.out.println("Bad pesel format");
            return;
        }
        if (peselNumber <= 0) {
            System.out.println("Bad pesel");
            return;
        }
        Reader reader;
        Optional<Reader> optionalReader = readerRepository.findById(peselNumber);
        if (optionalReader.isEmpty()) {
            System.out.println("Reader with this pesel not found. Will be created");
            reader = new Reader(peselNumber, name.getText());
//            readerRepository.save(reader);
            User user = new User(login, password, Role.U, email.getText(), reader);

            reader.setUser(user);

            userRepository.save(user);
            System.out.println("SAVED!");

//            System.out.println(userRepository.findByReader(reader));
            System.out.println("User added successfully. Login: " + user.getLogin());
        } else {
//            reader = optionalReader.get(); // not implemented
            System.out.println("Reader alread exists. Need to assign user to reader");
        }

        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
