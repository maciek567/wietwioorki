package pl.wietwioorki.to22019.controller;

import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.EmailUtil;

import java.util.Date;
import java.util.List;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;
import static pl.wietwioorki.to22019.util.InfoMessage.reservationSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class BooksListController extends AbstractWindowController { //todo

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
    private void initialize() {
        booksTable.setItems(getObservableItems(sessionConstants.getBookRepository().findAll()));

        idColumn.setCellValueFactory(dataValue -> dataValue.getValue().getIdProperty());
        titleColumn.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
        ratingColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAverageRatingProperty().asObject());
    }

    private ObservableListBase<Book> getObservableItems(List<Book> books) {
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

    @FXML
    public void handleAddReservationFromBookList(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");

        Reader reader = sessionConstants.getCurrentReader();
        if (reader == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, emptySelectionErrorHeader, userNotLoggedInErrorContent);
            return;
        }

        Book book = booksTable.getSelectionModel().getSelectedItem();
        if (book == null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, emptySelectionErrorHeader, noBookSelectedErrorContent);
            return;
        }

        ReservationStatus reservationStatus = book.isReaderQueueEmpty() ? ReservationStatus.READY : ReservationStatus.PENDING;

        book.pushReaderToQueue(reader);

        Reservation reservation = new Reservation(reader, book, null /*todo: today? */, null, reservationStatus);
        sessionConstants.getReservationRepository().save(reservation);

        EmailUtil.handleEmail(sessionConstants, reader);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, reservationSuccessfullyCreatedContent);
    }

}
