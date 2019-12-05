package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class AddReservationController {

    @FXML
    public TextField reservationBookTittle;

    @FXML
    public Button addReservation;

    @FXML
    public void handleAddReservation(ActionEvent actionEvent) {
    }
}
