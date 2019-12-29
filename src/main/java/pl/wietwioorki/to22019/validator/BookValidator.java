package pl.wietwioorki.to22019.validator;

import javafx.scene.control.*;
import lombok.Getter;
import org.springframework.stereotype.Component;
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
@Component
public class BookValidator extends MyValidator {
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
        author = authorRepository.findByFullName(authorName);
        if (author == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewAuthorBeCreatedHeader, shouldNewAuthorBeCreatedContent);
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonBar.ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonBar.ButtonData.YES) {
                    author = new Author(authorName);
                    authorRepository.save(author);
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
        this.genre = genreRepository.findByName(genre);
        if (this.genre == null) {
            Alert confirmationAlert = AlertFactory.createAlert(Alert.AlertType.CONFIRMATION, shouldNewGenreBeCreatedHeader, shouldNewGenrerBeCreatedContent);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                ButtonBar.ButtonData buttonData = result.get().getButtonData();
                if (buttonData == ButtonBar.ButtonData.YES) {

                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Text input dialog");
                    dialog.setHeaderText("Genre creation dialog");
                    dialog.setContentText("Please enter gender description:");

                    Optional<String> input = dialog.showAndWait();
                    if (input.isPresent()) {
                        //  and add book to this newly-created genre!
                        this.genre = new Genre(genre, input.get());
                        genreRepository.save(this.genre);
                        System.out.println("Created new genre");
                        return true;
                    }
                    return false;
                } else if (buttonData == ButtonBar.ButtonData.NO) {
                    System.out.println("Did not create new genre");
                    return false;
                }
            }
        }
        return true;
    }
}
