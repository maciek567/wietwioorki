package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;

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
        Reader reader = ReaderDAO.findByPesel(peselNumber);

        if(reader == null){
            System.out.println("Can't find reader with pesel:" + peselNumber);
            return;
        }

        long bookID;
        try {
            bookID = Long.parseLong(reservationBookId.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Can't read book id :" + reservationBookId.getText());
            return;
        }
        Book book = BookDAO.findById(bookID);

        if(book == null){
            System.out.println("Can't find book with id:" + bookID);
            return;
        }

        Date borrowingDate = null;
        Date returnDate = null;

        ReservationStatus reservationStatus = ReservationStatus.P;
        if(book.isEmpty()) reservationStatus = ReservationStatus.R;

        Reservation reservation = new Reservation(DataGenerator.generateId(), reader, book, borrowingDate, returnDate, reservationStatus);

        ReservationDAO.addReservation(reservation);

        System.out.println("Reservation added succesfully");
    }
}
