package pl.wietwioorki.to22019.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;

import java.util.ArrayList;
import java.util.Arrays;
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
    public TableColumn<Reservation, ReservationStatus> reservationStatus;

    @FXML
    private TableColumn<Reservation, Date> borrowingDate;

    @FXML
    private TableColumn<Reservation, Date> returnDate;

    @FXML
    private ComboBox selectedFilter;

    @FXML
    private TextField filterField;

    @FXML
    private void initialize() {
        reservationId.setCellValueFactory(dataValue -> dataValue.getValue().getReservationIdProperty());
        readerPesel.setCellValueFactory(dataValue -> dataValue.getValue().getReaderPeselProperty());
        readerName.setCellValueFactory(dataValue -> dataValue.getValue().getReaderNameProperty());
        booksTittle.setCellValueFactory(dataValue -> dataValue.getValue().getBooksTittleProperty());
        reservationStatus.setCellValueFactory(dataValue -> dataValue.getValue().getReservationStatusProperty());
        borrowingDate.setCellValueFactory(dataValue -> dataValue.getValue().getBorrowingDateProperty());
        returnDate.setCellValueFactory(dataValue -> dataValue.getValue().getReturnDateProperty());

        reservationTable.setItems(inicjujFiltry());

        selectedFilter.setItems(getFilterItems(true));
    }

    private boolean compareSelectedFilter(FilterValue expected){
        return selectedFilter.getSelectionModel().getSelectedItem().equals(expected);
    }

    @FXML
    public void handleChangeSelectedFilter(ActionEvent actionEvent){
        boolean visible = true;
        if(compareSelectedFilter(FilterValue.Expiration)){
            visible = false;
        }
        filterField.setVisible(visible);
        filterField.setText(".");
        filterField.setText("");
        reservationTable.refresh();
    }

    @FXML
    public void handleBorrowBookFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            return;
        }

        if(!reservation.getReservationStatus().equals(ReservationStatus.READY)) {
            System.out.println("Wrong reservation status: " + reservation.getReservationStatus());
            return;
        }

        reservation.setReservationStatus(ReservationStatus.ACTIVE);

        Calendar calendar = Calendar.getInstance();
        reservation.setReservationStartDate(calendar.getTime());

        calendar.add(Calendar.DATE, Reservation.getBorrowingTimeInDays());
        reservation.setReservationEndDate(calendar.getTime());

        reservation.borrowBook();
        System.out.println("Book borrowed successfully");
        reservationTable.refresh();
    }

    @FXML
    public void handleReturnBookFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            return;
        }

        reservation.setReservationStatus(ReservationStatus.RETURNED);

        reservation.returnBook();
        System.out.println("Book returned successfully");
        reservationTable.refresh();
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
        reservationTable.refresh();
    }

    private ObservableList getFilterItems(boolean isAdmin){
        ArrayList list = new ArrayList();

        list.addAll(Arrays.asList(FilterValue.values()));
        if(!isAdmin) {
            list.remove(FilterValue.Pesel);
        }
        return FXCollections.observableList(list);
    }

    private FilteredList<Reservation> inicjujFiltry(){
        FilteredList<Reservation> filteredData = new FilteredList<>(ReservationDAO.getReservationsObservable(), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || selectedFilter.getSelectionModel().getSelectedItem() == null) {
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.Expiration)){
                    if(reservation.getReservationStatus().equals(ReservationStatus.ACTIVE) && reservation.getBorrowingDateProperty().getValue().compareTo(new Date()) > 0) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else if(newValue.isEmpty()){
                    return true;
                }

                String caseFilter = newValue;
                if (compareSelectedFilter(FilterValue.Pesel) && reservation.getReaderPeselProperty().getValue().toString().startsWith(caseFilter)) {
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.ReservationID) && reservation.getReservationId().toString().startsWith(caseFilter)){
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.BookTitle) && reservation.getBooksTittleProperty().toString().startsWith(caseFilter)){
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.BorrowDate) && reservation.getBorrowingDateProperty().toString().startsWith(caseFilter)){
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.ReturnDate) && reservation.getReturnDateProperty().toString().startsWith(caseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        return filteredData;
    }
}

enum FilterValue{
    Pesel, BorrowDate, ReturnDate, Expiration, BookTitle, ReservationID
}