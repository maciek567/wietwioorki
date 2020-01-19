package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.RegistrationValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.readerSuccessfullyEditedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class EditProfileController extends AbstractWindowController{
    @FXML
    public TextField name;

    @FXML
    public TextField email;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPasswordConfirmation;

    @FXML
    public Button confirmation;

    @FXML
    private void initialize() {
        name.setText(sessionConstants.getCurrentReader().getFullName());
        email.setText(sessionConstants.getCurrentUser().getEmail());
    }

    @FXML
    public void handleEdit(ActionEvent actionEvent) {
        System.out.println("Editing reader");

        RegistrationValidator registrationValidator = sessionConstants.getRegistrationValidator();
        if (!registrationValidator.validateName(name.getText()) || !registrationValidator.validateEmail(email.getText()) ||
                !registrationValidator.validatePasswords(newPassword.getText(), newPasswordConfirmation.getText())) {
            return;
        }

        sessionConstants.getReaderRepository().updateFullName(sessionConstants.getCurrentReader().getPesel(),
                name.getText());

        sessionConstants.getUserRepository().updateEmail(sessionConstants.getCurrentUser().getLogin(),
                email.getText());

        sessionConstants.getUserRepository().updatePassword(sessionConstants.getCurrentUser().getLogin(),
                newPassword.getText());


        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, readerSuccessfullyEditedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
