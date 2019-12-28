package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.repository.ReaderRepository;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.ReaderValidator;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Date;

import static pl.wietwioorki.to22019.util.ErrorMessage.generalErrorHeader;
import static pl.wietwioorki.to22019.util.ErrorMessage.wrongDateErrorContent;
import static pl.wietwioorki.to22019.util.InfoMessage.readerSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class AddReaderController extends AbstractWindowController {

    @Autowired
    ReaderRepository repository;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public TextField pesel;

    @FXML
    public DatePicker birthDate = new DatePicker();

    @FXML
    public Button addReaderButton;

    @FXML
    public void handleAddNewReader(ActionEvent actionEvent) {
        System.out.println("Adding new reader");

        ReaderValidator readerValidator = new ReaderValidator();
        if (!readerValidator.validateNames(name.getText(), surname.getText()) || !readerValidator.validatePesel(pesel.getText())) {
            return;
        }

        Date date; // todo: remove
        try {
            date = constants.datePickerConverter(birthDate);
        } catch (ParseException | DateTimeException | NullPointerException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", wrongDateErrorContent);
            return;
        }

        Long peselNumber = Long.parseLong(pesel.getText());

        Reader reader = new Reader(peselNumber, name.getText() + " " + surname.getText(), date);
        repository.save(reader);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, readerSuccessfullyCreatedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
