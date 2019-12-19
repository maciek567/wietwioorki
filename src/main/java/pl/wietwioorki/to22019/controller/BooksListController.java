package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;

import java.util.Calendar;
import java.util.Date;

@Controller
public class BooksListController { //todo

    @Setter
    private static Stage primaryStage;

    @Autowired
    private ConfigurableApplicationContext springContext;

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
    private void initialize() {
        booksTable.setItems(BookDAO.getBooksObservable());

        idColumn.setCellValueFactory(dataValue -> dataValue.getValue().getIdProperty());
        titleColumn.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
    }

    @FXML
    public void handleAddReservationFromBookList(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");

        // todo - should be currently lloged in user
        Calendar calendar = Calendar.getInstance();
        calendar.set(1998, Calendar.JUNE, 25);
        Reader reader = new Reader(98062523456L, "Dawid", calendar.getTime());

        Book book = booksTable.getSelectionModel().getSelectedItem();
        if(book == null){
            System.out.println("No book selected");
            return;
        }

        ReservationStatus reservationStatus = ReservationStatus.P;
        if(book.isEmpty()) reservationStatus = ReservationStatus.R;

        Reservation reservation = new Reservation(DataGenerator.generateId(), reader, book, null, null, reservationStatus);

        ReservationDAO.addReservation(reservation);

        System.out.println("Reservation added succesfully");
    }

}
