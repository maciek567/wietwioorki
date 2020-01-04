package pl.wietwioorki.to22019.controller;

import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.repository.BookRepository;
import pl.wietwioorki.to22019.repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BooksListController extends AbstractWindowController { //todo

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReservationRepository reservationRepository;

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
        booksTable.setItems(getObservableItems(bookRepository.findAll()));

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

        /*Calendar calendar = Calendar.getInstance();
        calendar.set(1998, Calendar.JUNE, 25);
        Reader reader = new Reader(98062523456L, "Dawid", calendar.getTime());
*/
        Reader reader = constants.getCurrentReader();
//todo: add validator
        if (reader == null) {
            return;
        }

        Book book = booksTable.getSelectionModel().getSelectedItem();
        if (book == null) {
            System.out.println("No book selected");
            return;
        }

        ReservationStatus reservationStatus;
        if (book.isReaderQueueEmpty()) {
            reservationStatus = ReservationStatus.READY;
        } else {
            reservationStatus = ReservationStatus.PENDING;
        }

        book.pushReaderToQueue(reader);

        Reservation reservation = new Reservation(reader, book, null /*todo: today? */, null, reservationStatus);

        reservationRepository.save(reservation);


        System.out.println("Reservation added successfully");
    }

}
