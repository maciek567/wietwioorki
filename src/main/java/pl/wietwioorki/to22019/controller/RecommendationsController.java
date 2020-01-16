package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.enterValidUserLoginContent;
import static pl.wietwioorki.to22019.util.ErrorMessage.noSuchUserHeader;

@Controller
public class RecommendationsController extends AbstractWindowController {

    @FXML
    private TextField userName;

    @FXML
    private TableView<Book> booksFromUsersGenres;

    @FXML
    private TableView<Book> booksFromUsersAuthors;

    @FXML
    private TableView<Book> mostPopular;

    @FXML
    private Button recommendationsButton;

    @FXML public void recommendBooks(ActionEvent actionEvent){
        System.out.println("Searching for " + userName.getText());

        if (!sessionConstants.getCredentialsValidator().validateLogin(sessionConstants, userName.getText())) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, noSuchUserHeader, enterValidUserLoginContent);
        }else{
            System.out.println("haha");
        }

    }



}
