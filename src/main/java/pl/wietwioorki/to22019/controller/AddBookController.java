package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.AuthorDAO;
import pl.wietwioorki.to22019.dao.GenreDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;

import java.util.Date;

@Controller
public class AddBookController {

    @FXML
    public TextField bookTitle;

    @FXML
    public TextField author;

    @FXML
    public TextField publicationDate;

    @FXML
    public TextField genre;

    @FXML
    public Button addBookButton;

    @FXML
    public void handleAddNewBook(ActionEvent actionEvent) {
        System.out.println("Added new book");
        Book book = new Book(DataGenerator.generateId(), bookTitle.getText(), AuthorDAO.findById(Integer.parseInt(author.getText())), new Date(),
                //fixme /*publicationDate.getText() */
                GenreDAO.findById(Integer.parseInt(genre.getText())));
    }
}
