package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.InfoMessage.successfulLogout;

@Controller
public class ProfileController extends AbstractWindowController {

    @FXML
    public Button enterLogin;

    @FXML
    public Text loggedInUser;

    @FXML
    public Button enterRegistration;

    @FXML
    public Button enterNotificationSettings;

    @FXML
    public Button enterEditProfile;

    @FXML
    public void handleEnterLogin(ActionEvent actionEvent) {
        if (sessionConstants.getUserLogin() == null) {
            System.out.println("login view");
            openNewWindow("/layouts/Login.fxml");
            if (sessionConstants.getUserLogin() != null) {
                loggedInUser.setText(sessionConstants.getUserLogin());
                enterLogin.setText("Logout");
//                enableSettingsButton();
            }
        } else {
            sessionConstants.logoutUser();
            loggedInUser.setText("guest");
            enterLogin.setText("Login");
//            disableSettingsButton();
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, successfulLogout,
                    "You have successfully logout");
        }
//        refreshTabs();
    }

    @FXML
    public void handleEnterRegistration(ActionEvent actionEvent) {
        System.out.println("Registration view");
        openNewWindow("/layouts/Register.fxml");
    }

    @FXML
    public void handleEnterNotificationSettings(ActionEvent actionEvent) {
        System.out.println("Show notification settings");
        openNewWindow("/layouts/NotificationSettings.fxml");
    }

    @FXML
    public void handleEnterEditProfile(ActionEvent actionEvent) {
        System.out.println("Show notification settings");
        openNewWindow("/layouts/EditProfile.fxml");
    }

}
