package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.model.Role;
import pl.wietwioorki.to22019.model.User;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.RegistrationValidator;

import java.util.HashMap;

import static pl.wietwioorki.to22019.util.InfoMessage.readerSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class EditProfileController extends AbstractWindowController{
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
    public Button confirmation;

    @FXML
    public void handleEdit(ActionEvent actionEvent) {
        System.out.println("Editing reader");

        RegistrationValidator registrationValidator = sessionConstants.getRegistrationValidator();
        if (!registrationValidator.validateName(name.getText()) || !registrationValidator.validatePesel(pesel.getText()) ||
                !registrationValidator.validateUser(login.getText()) ||
                !registrationValidator.validateEmail(email.getText()) ||
                !registrationValidator.validatePasswords(registrationPassword.getText(), passwordConfirmation.getText())) {
            return;
        }

        Long peselNumber = Long.parseLong(pesel.getText());

        Reader reader = sessionConstants.getCurrentReader();
        User user = sessionConstants.getCurrentUser();


        sessionConstants.getReaderRepository().delete(reader);
        sessionConstants.getUserRepository().delete(user);

        sessionConstants.getReaderRepository().flush();
        sessionConstants.getUserRepository().flush();

        reader.setFullName(name.getText());
        reader.setPesel(peselNumber);
        user.setLogin(login.getText());
        user.setEmail(email.getText());
        user.setPassword(registrationPassword.getText());

        reader.setUser(user);
        sessionConstants.getUserRepository().save(user);
        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, readerSuccessfullyCreatedContent);

        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
