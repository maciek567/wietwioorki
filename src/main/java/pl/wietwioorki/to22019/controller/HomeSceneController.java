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
public class HomeSceneController extends AbstractWindowController {

    @FXML
    public Button enterLogin;

    @FXML
    public Button enterRegistration;

    @FXML
    public Text loggedInUser;

    @FXML
    public Button enterNotificationSettings;

    @FXML
    private Button addBookButton;

    @FXML
    private Button showBookListButton;

    @FXML
    private Button showFinesButton;

    @FXML
    public Button showReservationListButton;

    @FXML
    public Button showCompleteReservationListButton;

    @FXML
    public Button showStatisticsButton;

    @FXML
    private void initialize() {
        refreshButtons();
    }

    @FXML
    public void handleEnterLogin(ActionEvent actionEvent) {
        if (sessionConstants.getUserLogin() == null) {
            System.out.println("login view");
            openNewWindow("/layouts/Login.fxml");
            if (sessionConstants.getUserLogin() != null) {
                loggedInUser.setText(sessionConstants.getUserLogin());
                enterLogin.setText("Logout");
                enableSettingsButton();
            }
        } else {
            sessionConstants.logoutUser();
            loggedInUser.setText("guest");
            enterLogin.setText("Login");
            disableSettingsButton();
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, successfulLogout,
                    "You have successfully logout");
        }
        refreshButtons();
    }

    @FXML
    public void handleEnterRegistration(ActionEvent actionEvent) {
        System.out.println("Registration view");
        openNewWindow("/layouts/Register.fxml");
    }

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        if (isCurrentUserAdmin()) {
            System.out.println("New book view");
            openNewWindow("/layouts/AddBook.fxml");
        } else {
            showAdministratorNeededAlert();
        }
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        System.out.println("Show book list");
        openNewWindow("/layouts/BooksList.fxml");
    }

    @FXML
    public void handleShowFines(ActionEvent actionEvent) {
        if (!isCurrentUserGuest()) {
            System.out.println("Show fines");
            openNewWindow("/layouts/FineList.fxml");
        } else {
            showLogInNeededAlert();
        }
    }

    @FXML
    public void handleShowReservationList(ActionEvent actionEvent) {
        if (!isCurrentUserGuest()) {
            System.out.println("Show reservation list");
            openNewWindow("/layouts/ReservationList.fxml");
        } else {
            showLogInNeededAlert();
        }
    }

    @FXML
    public void handleShowCompleteReservationList(ActionEvent actionEvent) {
        if (!isCurrentUserGuest()) {
            System.out.println("Show complete reservation list");
            openNewWindow("/layouts/CompleteReservationList.fxml");
        } else {
            showLogInNeededAlert();
        }
    }

    @FXML
    public void handleShowStatisticsButton(ActionEvent actionEvent) {
        System.out.println("Show return book");
        openNewWindow("/layouts/Statistics.fxml");
    }

    @FXML
    public void handleEnterNotificationSettings(ActionEvent actionEvent) {
        System.out.println("Show notification settings");
        openNewWindow("/layouts/NotificationSettings.fxml");
    }

    public void enableSettingsButton() {
        enterNotificationSettings.setDisable(false);
    }

    public void disableSettingsButton() {
        enterNotificationSettings.setDisable(true);
    }

    private void refreshButtons() {
        addBookButton.setVisible(isCurrentUserAdmin());
        showFinesButton.setVisible(!isCurrentUserGuest());
        showReservationListButton.setVisible(!isCurrentUserGuest());
        showCompleteReservationListButton.setVisible(!isCurrentUserGuest());
    }
}
