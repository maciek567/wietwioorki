package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.BookValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.bookSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

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

        BookValidator bookValidator = sessionConstants.getBookValidator();

        if (!bookValidator.validateTitle(bookTitle.getText()) || !bookValidator.validateAuthor(authorName.getText()) ||
                !bookValidator.validateDate(sessionConstants, publicationDate) || !bookValidator.validateGenre(genre.getText())) {
            return;
        }

        sessionConstants.getBookRepository().save(new Book(bookTitle.getText(), bookValidator.getAuthor(),
                bookValidator.getDate(), bookValidator.getGenre()));

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyCreatedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        openNewWindow("/layouts/BooksList.fxml");
    }
}
