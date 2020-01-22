package pl.wietwioorki.to22019.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.CompleteReservation;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.util.Date;
import java.util.List;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Controller
public class RecommendationsController extends AbstractWindowController {

    @FXML
    private TextField userName;

    @FXML
    private TableView<Book> booksFromUsersGenres;

    @FXML
    private TableView<Book> booksFromUsersAuthors;

    @FXML
    private TableView<Book> mostPopular;

    @FXML
    private Button recommendationsButton;

    @FXML
    private TableColumn<Book, String> titleColumn1;
    @FXML
    private TableColumn<Book, String> authorColumn1;
    @FXML
    private TableColumn<Book, Date> dateColumn1;
    @FXML
    private TableColumn<Book, String> genreColumn1;
    @FXML
    public TableColumn<Book, Double> ratingColumn1;

    @FXML
    private TableColumn<Book, String> titleColumn2;
    @FXML
    private TableColumn<Book, String> authorColumn2;
    @FXML
    private TableColumn<Book, Date> dateColumn2;
    @FXML
    private TableColumn<Book, String> genreColumn2;
    @FXML
    public TableColumn<Book, Double> ratingColumn2;

    @FXML
    private TableColumn<Book, String> titleColumn3;
    @FXML
    private TableColumn<Book, String> authorColumn3;
    @FXML
    private TableColumn<Book, Date> dateColumn3;
    @FXML
    private TableColumn<Book, String> genreColumn3;
    @FXML
    public TableColumn<Book, Double> ratingColumn3;

    @FXML
    private void initialize() {

        titleColumn1.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn1.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn1.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn1.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
        ratingColumn1.setCellValueFactory(dataValue -> dataValue.getValue().getAverageRatingProperty().asObject());

        titleColumn2.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn2.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn2.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn2.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
        ratingColumn2.setCellValueFactory(dataValue -> dataValue.getValue().getAverageRatingProperty().asObject());

        titleColumn3.setCellValueFactory(dataValue -> dataValue.getValue().getTitleProperty());
        authorColumn3.setCellValueFactory(dataValue -> dataValue.getValue().getAuthorProperty());
        dateColumn3.setCellValueFactory(dataValue -> dataValue.getValue().getDateProperty());
        genreColumn3.setCellValueFactory(dataValue -> dataValue.getValue().getGenreProperty());
        ratingColumn3.setCellValueFactory(dataValue -> dataValue.getValue().getAverageRatingProperty().asObject());

    }

    @FXML
    public void recommendBooks(ActionEvent actionEvent) {
        System.out.println("Searching for " + userName.getText());

        if (!sessionConstants.getCredentialsValidator().validateLogin(sessionConstants, userName.getText())) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, noSuchUserHeader, enterValidUserLoginContent);
        } else {
            List<CompleteReservation> reservations = sessionConstants.getCompleteReservationRepository()
                    .findByReader(sessionConstants.getUserRepository().findByLogin(userName.getText()).getReader());

            if (reservations.isEmpty()) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, noReservationsHeader, noReservationsForRecommendationsContent);
            } else {
                reservations.sort(CompleteReservation::compareTo);
                CompleteReservation lastReservation = reservations.get(0);
                List<Book> mostPopularBooks = sessionConstants.getBookRepository().findAll();

                List<Book> matchingAuthor = sessionConstants.getBookRepository().findBooksByAuthor(lastReservation.getBook().getAuthor());
                List<Book> matchingGenre = sessionConstants.getBookRepository().findBooksByGenre(lastReservation.getBook().getGenre());
                mostPopularBooks.sort(Book::compareTo);

                matchingAuthor.subList(5, matchingAuthor.size()).clear();
                matchingGenre.subList(5, matchingGenre.size()).clear();
                mostPopularBooks.subList(5, mostPopularBooks.size()).clear();

                booksFromUsersGenres.setItems((ObservableList<Book>) matchingGenre);
                booksFromUsersAuthors.setItems((ObservableList<Book>) matchingAuthor);
                mostPopular.setItems((ObservableList<Book>) mostPopularBooks);

            }
        }

    }


}
