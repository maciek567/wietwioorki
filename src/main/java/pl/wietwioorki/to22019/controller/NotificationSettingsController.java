package pl.wietwioorki.to22019.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationSettingsController {

    @FXML
    public CheckBox readyBook;

    @FXML
    public CheckBox overdueBook;

    @FXML
    public CheckBox newReservation;

    @FXML
    public CheckBox borrowedBook;

    @FXML
    public CheckBox returnedBook;
}
