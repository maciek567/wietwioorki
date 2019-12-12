package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class BookBorrowController {

    @FXML
    public TextField pesel;

    @FXML
    public TextField reservationBookId;

    @FXML
    public Button addReservation;

    @FXML
    public void handleBorrowBook(ActionEvent actionEvent) {

    }
}
