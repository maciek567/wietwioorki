package pl.wietwioorki.to22019.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.EmailUtil;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;
import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public class BooksListController extends AbstractWindowController {
    enum FilterValue {
        Title, Author, Genre, PublicationDate, Id
    }

    @FXML
    public Button addReservationFromBookList;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Long> idColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Date> dateColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    public TableColumn<Book, Double> ratingColumn;

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
        idColumn.setCellValueFactory(dataValue -> dataValue.getValue().getIdProperty());
        titleColumn.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
        ratingColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAverageRatingProperty().asObject());

        refreshWindow();

        selectedFilter.setItems(getFilterItems());

        selectedFilter.getSelectionModel().select(0);

        dateFields.setVisible(false);

        sessionConstants.events.AddListener(this);
    }

    @FXML
    public void handleAddReservationFromBookList(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");

        Book book = booksTable.getSelectionModel().getSelectedItem();
        if (book == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, emptySelectionErrorHeader, noBookSelectedErrorContent);
            return;
        }

        Reader reader = sessionConstants.getCurrentReader();
        if (reader == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, loggedAsGuestHeader, userNotLoggedInErrorContent);
            return;
        }

        ReservationStatus reservationStatus = book.isReaderQueueEmpty(sessionConstants.getReservationRepository()) ? ReservationStatus.READY : ReservationStatus.PENDING;

        book.pushReaderToQueue(reader);

        // null because start date means time of borrowing, not reservation
        Reservation reservation = new Reservation(reader, book, null, null, reservationStatus);
        sessionConstants.getReservationRepository().save(reservation);

        sessionConstants.events.dataChanged();

        if (reservationStatus.equals(ReservationStatus.READY) &&
                sessionConstants.getCurrentUser().getNotificationSettings().get(ReservationStatus.READY)) {
            EmailUtil.handleEmail(sessionConstants, reader);
        } else if (reservationStatus.equals(ReservationStatus.PENDING) &&
                sessionConstants.getCurrentUser().getNotificationSettings().get(ReservationStatus.PENDING))
            EmailUtil.handleEmail(sessionConstants, reader);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, reservationSuccessfullyCreatedContent);
    }

    @FXML
    public void handleChangeDate(ActionEvent actionEvent) {
        refreshFilters();
    }

    @FXML
    public void handleChangeSelectedFilter(ActionEvent actionEvent) {
        boolean filterFieldVisible = true;
        boolean dateFieldsVisible = false;

        if (compareSelectedFilter(FilterValue.PublicationDate)) {
            dateFieldsVisible = true;
            filterFieldVisible = false;
        }

        filterField.setVisible(filterFieldVisible);
        dateFields.setVisible(dateFieldsVisible);
        refreshFilters();
        booksTable.refresh();
    }

    public void handleChangeUser() {
        refreshWindow();
    }

    public void handleChangeData() {
        refreshData();
    }

    private void refreshWindow() {
        refreshData();
        addReservationFromBookList.setVisible(!isCurrentUserGuest());
    }

    private void refreshData() {
        booksTable.setItems(InitializeFilters());
    }

    private void refreshFilters() {
        String filterText = filterField.getText();
        filterField.setText(filterText + " ");
        filterField.setText(filterText);
    }

    private boolean compareSelectedFilter(FilterValue expected) {
        return selectedFilter.getSelectionModel().getSelectedItem().equals(expected);
    }

    private ObservableList getFilterItems() {
        return FXCollections.observableList(Arrays.asList(FilterValue.values()));
    }

    private FilteredList<Book> InitializeFilters() {
        FilteredList<Book> filteredDataByFilter = new FilteredList<>(getBooksItems(), p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataByFilter.setPredicate(book -> {
                if (newValue == null || selectedFilter.getSelectionModel().getSelectedItem() == null) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.Id) && book.getBookId().toString().startsWith(newValue)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.Title) && book.getTitle().startsWith(newValue)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.Author) && authorCompare(newValue, book)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.Genre) && genreCompare(newValue, book)) {
                    return true;
                } else if (compareSelectedFilter(FilterValue.PublicationDate)) {
                    return checkDatesBetweenDatepickers(book.getPublicationDate());
                } else if (newValue.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        return filteredDataByFilter;
    }

    private boolean authorCompare(String authorField, Book book) {
        if (book.getAuthor().getFullName().startsWith(authorField)) {
            return true;
        }
        return false;
    }

    private boolean genreCompare(String genreField, Book book) {
        if (book.getGenre().getName().startsWith(genreField)) {
            return true;
        }
        return false;
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

    private ObservableListBase<Book> getBooksItems() {
        List<Book> books = sessionConstants.getBookRepository().findAll();
        return new ObservableListBase<Book>() { // do not remove Book from here - doesn't compile without it even though it's redundant...
            @Override
            public Book get(int index) {
                return books.get(index);
            }

            @Override
            public int size() {
                return books.size();
            }
        };
    }
}
