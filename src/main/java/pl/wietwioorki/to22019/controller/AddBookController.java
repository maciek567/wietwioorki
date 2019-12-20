package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.AuthorDAO;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.GenreDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

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
            System.out.println("We dont know this author.");
            return;
        }

        Date date;
        try {
            date = constants.datePickerConverter(publicationDate);
        } catch (ParseException | NullPointerException e) {
            System.out.println("Bad date");
            return;
        }

        Genre genreObject;
        genreObject = GenreDAO.findByName(genre.getText());
        if (genre == null) {
            System.out.println("We dont know this genre.");
            return;
        }

        Book book = new Book(DataGenerator.generateId(), bookTitle.getText(), authorObject, date, genreObject, new LinkedList<>());
        BookDAO.addBook(book);
        System.out.println("Book add successfully");
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        openNewWindow("/layouts/BooksList.fxml");
    }
}
