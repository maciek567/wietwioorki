package pl.wietwioorki.to22019.controller;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;

import java.util.Calendar;
import java.util.Date;

@Controller
public class ReservationListController {

    @Setter
    private static Stage primaryStage;


    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    public Button cancelReservationFromReservationList;

    @FXML
    public Button borrowBookFromReservationList;

    @FXML
    public Button returnBookFromReservationList;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Long> reservationId;

    @FXML
    private TableColumn<Reservation, Long> readerPesel;

    @FXML
    private TableColumn<Reservation, String> readerName;

    @FXML
    private TableColumn<Reservation, String> booksTittle;

    @FXML
    private TableColumn<Reservation, Date> borrowingDate;

    @FXML
    private TableColumn<Reservation, Date> returnDate;

    @FXML
    private TextField filterField;

    @FXML
    private void initialize() {
        reservationId.setCellValueFactory(dataValue -> dataValue.getValue().getReservationIdProperty());
        readerPesel.setCellValueFactory(dataValue -> dataValue.getValue().getReaderPeselProperty());
        readerName.setCellValueFactory(dataValue -> dataValue.getValue().getReaderNameProperty());
        booksTittle.setCellValueFactory(dataValue -> dataValue.getValue().getBooksTittleProperty());
        borrowingDate.setCellValueFactory(dataValue -> dataValue.getValue().getBorrowingDateProperty());
        returnDate.setCellValueFactory(dataValue -> dataValue.getValue().getReturnDateProperty());

        FilteredList<Reservation> filteredData = new FilteredList<>(ReservationDAO.getReservationsObservable(), p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String caseFilter = newValue;
                if (reservation.getReaderPeselProperty().getValue().toString().startsWith(caseFilter)) {
                    return true;
                }
                return false;
            });
        });

        reservationTable.setItems(filteredData);
    }

    @FXML
    public void handleBorrowBookFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            return;
        }

        if(!reservation.getReservationStatus().equals(ReservationStatus.R)) {
            System.out.println("Wrong reservation status: " + reservation.getReservationStatus());
            return;
        }

        reservation.setReservationStatus(ReservationStatus.A);

        Calendar calendar = Calendar.getInstance();
        reservation.setReservationStartDate(calendar.getTime());

        calendar.add(Calendar.DATE, Reservation.getBorrowingTimeInDays());
        reservation.setReservationEndDate(calendar.getTime());

        reservation.borrowBook();
        System.out.println("Book borrowed successfully");
    }

    @FXML
    public void handleReturnBookFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            return;
        }

        reservation.setReservationStatus(ReservationStatus.E);

        reservation.returnBook();
        System.out.println("Book returned successfully");
    }

    @FXML
    public void handleCancelReservationFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            return;
        }

        ReservationDAO.removeById(reservation.getReservationId());
        System.out.println("Reservation removed successfully");
    }
}
