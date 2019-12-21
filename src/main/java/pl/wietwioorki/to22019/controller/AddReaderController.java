package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.dao.UserDAO;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Role;
import pl.wietwioorki.to22019.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AddReaderController {

    @Setter
    private static Stage primaryStage;

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public TextField pesel;

    @FXML
    public TextField birthDate;

    @FXML
    public Button addReaderButton;

    @FXML
    public void handleAddNewReader(ActionEvent actionEvent) {
        System.out.println("Adding new reader");
        Long peselNumber = null;
        try {
            peselNumber = Long.parseLong(pesel.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Bad pesel format");
            return;
        }
        if(peselNumber==null || peselNumber<=0){
            System.out.println("Bad pesel");
            return;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate.getText());
        } catch (ParseException e) {
            System.out.println("Bad date");
            return;
        }
        if(date==null){
            System.out.println("Bad date");
            return;
        }

        User user = new User(peselNumber, Role.U, name.getText() + surname.getText(), "password");

        Reader reader = new Reader(Long.parseLong(pesel.getText()), name.getText() + " " + surname.getText(), date, user);

        UserDAO.addUser(user);
        ReaderDAO.addReader(reader);

        System.out.println("User added succesfully " + user.getLogin());
        System.out.println("Reader added succesfully");
    }
}
