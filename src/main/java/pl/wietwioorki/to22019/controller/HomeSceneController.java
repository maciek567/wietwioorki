package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.InfoMessage.successfulLogout;

@Controller
public class HomeSceneController extends AbstractWindowController {

    @FXML
    public Tab enterProfile;

    @FXML
    private Tab addBookTab;

    @FXML
    private Tab showBookListTab;

    @FXML
    private Tab showFinesTab;

    @FXML
    public Tab showReservationListTab;

    @FXML
    public Tab showCompleteReservationListTab;

    @FXML
    public Tab showStatisticsTab;

    @FXML
    private void initialize() {
        refreshTabs();
    }

    private void refreshTabs() {
        addBookTab.setDisable(!isCurrentUserAdmin());
        showFinesTab.setDisable(isCurrentUserGuest());
        showReservationListTab.setDisable(isCurrentUserGuest());
        showCompleteReservationListTab.setDisable(isCurrentUserGuest());
    }
}
