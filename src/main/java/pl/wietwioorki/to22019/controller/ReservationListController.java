package pl.wietwioorki.to22019.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.*;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.util.*;

import static pl.wietwioorki.to22019.util.ErrorMessage.generalErrorHeader;
import static pl.wietwioorki.to22019.util.ErrorMessage.noReservationSelectedErrorContent;
import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public class ReservationListController extends AbstractWindowController {
    enum FilterValue{
        Pesel, BorrowDate, ReturnDate, Expiration, BookTitle, ReservationID
    }

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
        booksTittle.setCellValueFactory(dataValue -> dataValue.getValue().getBooksTitleProperty());
        reservationStatus.setCellValueFactory(dataValue -> dataValue.getValue().getReservationStatusProperty());
        borrowingDate.setCellValueFactory(dataValue -> dataValue.getValue().getBorrowingDateProperty());
        returnDate.setCellValueFactory(dataValue -> dataValue.getValue().getReturnDateProperty());

        reservationTable.setItems(InitializeFilters());

        boolean isAdmin = false;
        if(sessionConstants.getCurrentUser() != null){
            isAdmin = sessionConstants.getCurrentUser().getRole().equals(Role.L);
        }

        selectedFilter.setItems(getFilterItems(isAdmin));
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
    public void handleBorrowBookFromReservationList(ActionEvent actionEvent) { //todo: add alerts
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            System.out.println("No reservation selected");
            // here
            return;
        }

        if(!reservation.getReservationStatus().equals(ReservationStatus.READY)) {
            System.out.println("Wrong reservation status: " + reservation.getReservationStatus());
            // here
            return;
        }

        reservation.setReservationStatus(ReservationStatus.ACTIVE);

        Calendar calendar = Calendar.getInstance();
        reservation.setReservationStartDate(calendar.getTime());

        calendar.add(Calendar.DATE, Reservation.getBorrowingTimeInDays());
        reservation.setReservationEndDate(calendar.getTime());

        reservation.borrowBook();
        sessionConstants.getReservationRepository().save(reservation);

        // for statistics - save counters of borrowed books
        Book borrowedBook = reservation.getBook();
        borrowedBook.incrementNoBorrows();
        sessionConstants.getBookRepository().save(borrowedBook);

        User loggedInUser = sessionConstants.getCurrentUser();
        loggedInUser.incrementNoBorrowings();
        sessionConstants.getUserRepository().save(loggedInUser);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyBorrowedContent);
        reservationTable.refresh();
    }

    @FXML
    public void handleReturnBookFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "returning book", noReservationSelectedErrorContent);
            return;
        }
        if(!reservation.getReservationStatus().equals(ReservationStatus.ACTIVE)){
            System.out.println("Bad reservation status");
            //todo: add alert
            return;
        }
        reservation.setReservationStatus(ReservationStatus.RETURNED);

        Fine fine = reservation.returnBook();
        if(fine != null){
            sessionConstants.getFineRepository().save(fine);
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, receiveFineInfoHeader, fine.getDescription() + "\nValue: " + fine.getValue());
        }
        sessionConstants.getReservationRepository().save(reservation);
        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyReturnedContent);

        reservationTable.refresh();
    }

    @FXML
    public void handleCancelReservationFromReservationList(ActionEvent actionEvent) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "cancelling reservation", noReservationSelectedErrorContent);
            return;
        }

        sessionConstants.getReservationRepository().delete(reservation);
        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, reservationSuccessfullyDeletedContent);

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

    private FilteredList<Reservation> InitializeFilters(){
        FilteredList<Reservation> filteredData = new FilteredList<>(getReservationsObservable(sessionConstants.getReservationRepository().findAll()), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || selectedFilter.getSelectionModel().getSelectedItem() == null) {
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.Expiration)){
                    return reservation.getReservationStatus().equals(ReservationStatus.ACTIVE) && reservation.getBorrowingDateProperty().getValue().compareTo(new Date()) > 0;
                }
                else if(newValue.isEmpty()){
                    return true;
                }

                if (compareSelectedFilter(FilterValue.Pesel) && reservation.getReaderPeselProperty().getValue().toString().startsWith(newValue)) {
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.ReservationID) && reservation.getReservationId().toString().startsWith(newValue)){
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.BookTitle) && reservation.getBooksTitleProperty().getValue().startsWith(newValue)){
                    return true;
                }
                else if(compareSelectedFilter(FilterValue.BorrowDate) && reservation.getBorrowingDateProperty().toString().startsWith(newValue)){
                    return true;
                }
                else return compareSelectedFilter(FilterValue.ReturnDate) && reservation.getReturnDateProperty().toString().startsWith(newValue);
            });
        });
        return filteredData;
    }

    private ObservableList<Reservation> getReservationsObservable(List<Reservation> reservations){
        return new ObservableListBase<Reservation>() {
            @Override
            public Reservation get(int index) {
                return reservations.get(index);
            }

            @Override
            public int size() {
                return reservations.size();
            }
        };
    }
}