package pl.wietwioorki.to22019.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.CompleteReservation;
import pl.wietwioorki.to22019.model.Reader;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class CompleteReservationListController extends AbstractWindowController {
    enum FilterValue {
        BookTitle, BorrowDate, ReservationID, WasOverdue
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
    private Text peselText;

    @FXML
    private TextField peselField;

    @FXML
    private ComboBox selectedFilter;

    @FXML
    private TextField filterField;

    @FXML
    private HBox dateFields;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private void initialize() {
        reservationId.setCellValueFactory(dataValue -> dataValue.getValue().getReservationIdProperty());
        readerPesel.setCellValueFactory(dataValue -> dataValue.getValue().getReaderPeselProperty());
        readerName.setCellValueFactory(dataValue -> dataValue.getValue().getReaderNameProperty());
        booksTittle.setCellValueFactory(dataValue -> dataValue.getValue().getBooksTitleProperty());
        borrowingDate.setCellValueFactory(dataValue -> dataValue.getValue().getBorrowingDateProperty());
        wasOverdue.setCellValueFactory(dataValue -> dataValue.getValue().getWasOverdueProperty());

        refreshWindow();

        selectedFilter.setItems(getFilterItems());

        selectedFilter.getSelectionModel().select(0);

        dateFields.setVisible(false);

        sessionConstants.events.AddListener(this);
    }

    private boolean compareSelectedFilter(FilterValue expected) {
        return selectedFilter.getSelectionModel().getSelectedItem().equals(expected);
    }

    @FXML
    public void handleChangeSelectedFilter(ActionEvent actionEvent) {
        boolean filterFieldVisible = true;
        boolean dateFieldsVisible = false;

        if (compareSelectedFilter(FilterValue.WasOverdue)) {
            filterFieldVisible = false;
        }

        if (compareSelectedFilter(FilterValue.BorrowDate)) {
            dateFieldsVisible = true;
            filterFieldVisible = false;
        }

        filterField.setVisible(filterFieldVisible);
        dateFields.setVisible(dateFieldsVisible);
        refreshFilters();
        reservationTable.refresh();
    }

    @FXML
    public void handleChangeDate(ActionEvent actionEvent) {
        refreshFilters();
    }

    private void refreshFilters() {
        String filterText = filterField.getText();
        filterField.setText(filterText + " ");
        filterField.setText(filterText);

        String peselText = peselField.getText();
        peselField.setText(peselText + " ");
        peselField.setText(peselText);
    }

    public void handleChangeUser() {
        refreshWindow();
    }

    public void handleChangeData() {
        refreshData();
    }

    private void refreshWindow() {
        refreshData();

        if (!isCurrentUserAdmin()) {
            peselText.setVisible(false);
            peselField.setVisible(false);
        }
    }

    private void refreshData() {
        reservationTable.setItems(InitializeFilters());
    }

    private ObservableList getFilterItems() {
        return FXCollections.observableList(Arrays.asList(FilterValue.values()));
    }

    private FilteredList<CompleteReservation> InitializeFilters() {
        FilteredList<CompleteReservation> filteredDataByPesel = new FilteredList<>(getReservationsObservable(), p -> true);
        peselField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataByPesel.setPredicate(reservation -> {
                if (isCurrentUserAdmin()) {
                    if (!(newValue == null || newValue.isEmpty() || reservation.getReaderPeselProperty().getValue().toString().startsWith(newValue))) {
                        return false;
                    }
                }
                return true;
            });
        });

        FilteredList<CompleteReservation> filteredDataByFilter = new FilteredList<>(filteredDataByPesel, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataByFilter.setPredicate(reservation -> {
                if (newValue == null || selectedFilter.getSelectionModel().getSelectedItem() == null) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.WasOverdue)) {
                    return reservation.getWasOverdue();
                } else if (compareSelectedFilter(FilterValue.ReservationID) && reservation.getReservationId().toString().startsWith(newValue)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.BookTitle) && reservation.getBooksTitleProperty().getValue().startsWith(newValue)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.BorrowDate)) {
                    return checkDatesBetweenDatepickers(reservation.getReservationStartDate());
                } else if (newValue.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        return filteredDataByFilter;
    }

    private ObservableList<CompleteReservation> getReservationsObservable() {
        List<CompleteReservation> reservations = null;
        if (isCurrentUserAdmin()) {
            reservations = sessionConstants.getCompleteReservationRepository().findAll();
        } else {
            Reader reader = sessionConstants.getCurrentReader();
            if (reader != null) {
                reservations = sessionConstants.getCompleteReservationRepository().findByReader(reader);
            } else {
                reservations = new ArrayList<>();
            }
        }
        List<CompleteReservation> finalReservations = reservations;
        return new ObservableListBase<CompleteReservation>() {
            @Override
            public CompleteReservation get(int index) {
                return finalReservations.get(index);
            }

            @Override
            public int size() {
                return finalReservations.size();
            }
        };
    }

    private boolean checkDatesBetweenDatepickers(Date checkedDate) {
        if (checkedDate == null) {
            return false;
        }
        return checkDateAfterDatepicker(checkedDate) && checkDateBeforeDatepicker(checkedDate);
    }

    private boolean checkDateAfterDatepicker(Date checkedDate) {
        if (dateFrom.getEditor().getText().equals("")) {
            return true;
        }
        Date dateFromValue;
        try {
            dateFromValue = sessionConstants.datePickerConverter(dateFrom);
        } catch (ParseException | DateTimeException | NullPointerException e) {
            return false;
        }
        if (dateFromValue.compareTo(checkedDate) > 0) {
            return false;
        }
        return true;
    }

    private boolean checkDateBeforeDatepicker(Date checkedDate) {
        if (dateTo.getEditor().getText().equals("")) {
            return true;
        }
        Date dateToValue;
        try {
            dateToValue = sessionConstants.datePickerConverter(dateTo);
        } catch (ParseException | DateTimeException | NullPointerException e) {
            return false;
        }
        if (dateToValue.compareTo(checkedDate) < 0) {
            return false;
        }
        return true;
    }
}