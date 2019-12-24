package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import lombok.Getter;
import pl.wietwioorki.to22019.dao.AuthorDAO;
import pl.wietwioorki.to22019.dao.GenreDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Genre;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.Constants;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Optional;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;
import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Getter
public class BookValidator {
    private Author author;
    private Date date;
    private Genre genre;

    public boolean validateTitle(String title) {
        if (title.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding book", "Book title" + emptyFieldErrorContent);
            return false;
        }
        return true;
    }

    public boolean validateAuthor(String authorName) {
        author = AuthorDAO.findByName(authorName);
        if (author == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewAuthorBeCreatedHeader, shouldNewAuthorBeCreatedContent);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonBar.ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonBar.ButtonData.YES) {

                    AuthorDAO.addAuthor(new Author(DataGenerator.generateId(), authorName)); // todo: id should not be generated
                    // todo: there should be a popup to fill ALL author's data
                    System.out.println("Created new author");
                    return true; // for the sake of readability...
                } else if (buttonData == ButtonBar.ButtonData.NO) {
                    System.out.println("Did not create new author");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateDate(Constants constants, DatePicker datePicker) {
        try {
            date = constants.datePickerConverter(datePicker);
        } catch (ParseException | DateTimeException | NullPointerException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding book", wrongDateErrorContent);
            return false;
        }
        return true;
    }

    public boolean validateGenre(String genre) {
        this.genre = GenreDAO.findByName(genre);
        if (this.genre == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewGenreBeCreatedHeader, shouldNewGenrerBeCreatedContent);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonBar.ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonBar.ButtonData.YES) {
                    // todo: add field for genre description
//                                        GenreDAO.addGenre(new Genre(DataGenerator.generateId(), genre.getText(), "")); // todo: id should not be generated
                    System.out.println("Created new genre");
                    return true;
                } else if (buttonData == ButtonBar.ButtonData.NO) {
                    System.out.println("Did not create new genre");
                    return false;
                }
            }
        }
        return true;
    }
}
