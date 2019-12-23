package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.AuthorDAO;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.GenreDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

import static javafx.scene.control.ButtonBar.ButtonData;
import static pl.wietwioorki.to22019.util.ErrorMessage.generalErrorHeader;
import static pl.wietwioorki.to22019.util.ErrorMessage.wrongDateErrorContent;
import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public class AddBookController extends AbstractWindowController {

    @FXML
    public TextField bookTitle;

    @FXML
    public TextField authorName;

    @FXML
    public DatePicker publicationDate;

    @FXML
    public TextField genre;

    @FXML
    public Button addBookButton;

    @FXML
    public void handleAddNewBook(ActionEvent actionEvent) {
        System.out.println("Adding new book");

        Author authorObject = AuthorDAO.findByName(authorName.getText());
        if (authorObject == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewAuthorBeCreatedHeader, shouldNewAuthorBeCreatedContent);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonData.YES) {
                    AuthorDAO.addAuthor(new Author(DataGenerator.generateId(), authorName.getText())); // todo: id should not be generated
                    System.out.println("Created new author");
                } else if (buttonData == ButtonData.NO) {
                    System.out.println("Did not create new author");
                    return;
                }
            }
        }

        Date date;
        try {
            date = constants.datePickerConverter(publicationDate);
        } catch (ParseException | DateTimeException | NullPointerException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding book", wrongDateErrorContent);
            return;
        }

        Genre genreObject;
        genreObject = GenreDAO.findByName(genre.getText());
        if (genre == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewGenreBeCreatedHeader, shouldNewGenrerBeCreatedContent);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonData.YES) {
                    //                    GenreDAO.addGenre(new Genre(DataGenerator.generateId(), genre.getText(), "")); // todo: id should not be generated
                    System.out.println("Created new genre");
                } else if (buttonData == ButtonData.NO) {
                    System.out.println("Did not create new genre");
                    return;
                }
            }
        }

        Book book = new Book(DataGenerator.generateId(), bookTitle.getText(), authorObject, date, genreObject, new LinkedList<>(), 0.0, 0);
        BookDAO.addBook(book);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyCreatedContent);
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        openNewWindow("/layouts/BooksList.fxml");
    }
}
