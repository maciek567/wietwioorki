package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AddReservationController {

    @FXML
    public TextField pesel;

    @FXML
    public TextField reservationBookId;

    @FXML
    public Button addReservation;

    @FXML
    public void handleAddReservation(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");
        Long peselNumber = null;
        try {
            peselNumber = Long.parseLong(pesel.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Bad pesel format");
            return;
        }
        if(peselNumber==null || peselNumber<=0){
            System.out.println("Bad pesel");
            return;
        }
        Reader reader = ReaderDAO.findByName(pesel.getText());

        Book book = BookDAO.findById(reservationBookId.getId());

        Date borrowingDate;
        Date returnDate;
        try {
            borrowingDate = new SimpleDateFormat("dd/MM/yyyy").parse("26/12/2019");
            returnDate = new SimpleDateFormat("dd/MM/yyyy").parse("26/12/2019");
        } catch (ParseException e) {
            System.out.println("Bad date");
            return;
        }
        if(borrowingDate==null || returnDate==null){
            System.out.println("Bad date");
            return;
        }
        Reservation reservation = new Reservation(1L, reader, book, borrowingDate, returnDate);

        ReservationDAO.addReservation(reservation);

        System.out.println("Reservation added succesfully");
    }
}
