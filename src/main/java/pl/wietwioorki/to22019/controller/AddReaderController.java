package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.model.Reader;

import java.text.ParseException;
import java.util.Date;

@Controller
public class AddReaderController extends AbstractWindowController {
    @Setter
    private static Stage primaryStage;

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
        long peselNumber = Long.parseLong(pesel.getText());

        if (peselNumber <= 0) {
            System.out.println("Bad pesel");
            return;
        }

        Date date;
        try {
            date = constants.datePickerConverter(birthDate);
        } catch (ParseException | NullPointerException e) {
            System.out.println("Bad date");
            return;
        }

        Reader reader = new Reader(Long.parseLong(pesel.getText()), name.getText() + " " + surname.getText(), date);

        ReaderDAO.addReader(reader);

        System.out.println("Reader added successfully. All readers: ");

        for (Reader r : ReaderDAO.getReaders()) {
            System.out.println("READER: " + r);
        }
    }
}
