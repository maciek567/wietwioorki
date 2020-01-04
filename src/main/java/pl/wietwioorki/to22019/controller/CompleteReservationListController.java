package pl.wietwioorki.to22019.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.CompleteReservation;
import pl.wietwioorki.to22019.model.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class CompleteReservationListController extends AbstractWindowController {

    enum FilterValue {
        Pesel, BorrowDate, ReturnDate, BookTitle, ReservationID, WasOverdue
    }

    @FXML
    private TableView<CompleteReservation> reservationTable;

    @FXML
    private TableColumn<CompleteReservation, Long> reservationId;

    @FXML
    private TableColumn<CompleteReservation, Long> readerPesel;

    @FXML
    private TableColumn<CompleteReservation, String> readerName;

    @FXML
    private TableColumn<CompleteReservation, String> booksTittle;

    @FXML
    private TableColumn<CompleteReservation, Date> borrowingDate;

    @FXML
    public TableColumn<CompleteReservation, Boolean> wasOverdue;

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
        borrowingDate.setCellValueFactory(dataValue -> dataValue.getValue().getBorrowingDateProperty());
        wasOverdue.setCellValueFactory(dataValue -> dataValue.getValue().getWasOverdueProperty());
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
        filterField.setVisible(true);
        filterField.setText(".");
        filterField.setText("");
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

    private FilteredList<CompleteReservation> InitializeFilters(){
        FilteredList<CompleteReservation> filteredData = new FilteredList<>(getReservationsObservable(sessionConstants.getCompleteReservationRepository().findAll()), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {
                if (newValue == null || selectedFilter.getSelectionModel().getSelectedItem() == null) {
                    return true;
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
                else return compareSelectedFilter(FilterValue.WasOverdue) && reservation.getWasOverdueProperty().toString().startsWith(newValue);
            });
        });
        return filteredData;
    }

    private ObservableList<CompleteReservation> getReservationsObservable(List<CompleteReservation> reservations){
        return new ObservableListBase<CompleteReservation>() {
            @Override
            public CompleteReservation get(int index) {
                return reservations.get(index);
            }

            @Override
            public int size() {
                return reservations.size();
            }
        };
    }
}