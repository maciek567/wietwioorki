package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.BookValidator;

import java.util.LinkedList;

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

        BookValidator bookValidator = new BookValidator();

        if (!bookValidator.validateTitle(bookTitle.getText()) || !bookValidator.validateAuthor(authorName.getText()) || !bookValidator.validateDate(constants, publicationDate) || !bookValidator.validateGenre(genre.getText())) {
            return;
        }

        BookDAO.addBook(new Book(DataGenerator.generateId(), bookTitle.getText(), bookValidator.getAuthor(),
                bookValidator.getDate(), bookValidator.getGenre(), new LinkedList<>(), 0.0, 0));

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyCreatedContent);
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        openNewWindow("/layouts/BooksList.fxml");
    }
}
