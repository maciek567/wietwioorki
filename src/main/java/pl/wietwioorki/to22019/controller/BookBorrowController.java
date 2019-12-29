package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.repository.ReservationRepository;

import java.util.Optional;

@Controller
public class BookBorrowController extends AbstractWindowController{

    @Autowired
    ReservationRepository reservationRepository;

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
        Optional<Reservation> reservation = reservationRepository.findById(reservationID);

        if(reservation.isEmpty()){
            System.out.println("Can't find reservation with id:" + reservationID);
            return;
        }
        reservation.get().borrowBook();
        System.out.println("Book borrowed successfully");
    }
}
