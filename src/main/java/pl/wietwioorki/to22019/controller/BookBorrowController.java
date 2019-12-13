package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.model.Reservation;

@Controller
public class BookBorrowController {

    @FXML
    public TextField pesel;

    @FXML
    public TextField reservationId;

    @FXML
    public Button addReservation;

    @FXML
    public void handleBorrowBook(ActionEvent actionEvent) {
        long reservationID;
        try {
            reservationID = Long.parseLong(reservationId.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Can't read reservation id :" + reservationId.getText());
            return;
        }
        Reservation reservation = ReservationDAO.findById(reservationID);

        if(reservation == null){
            System.out.println("Can't find reservation with id:" + reservationID);
            return;
        }
        reservation.borrowBook();
        System.out.println("Book borrowed succesfully");
    }
}
