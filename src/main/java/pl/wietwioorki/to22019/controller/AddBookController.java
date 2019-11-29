package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.AuthorDAO;
import pl.wietwioorki.to22019.dao.BookDAO;
import pl.wietwioorki.to22019.dao.GenreDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Author;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Genre;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AddBookController {

    @Setter
    private static Stage primaryStage;


    @Autowired
    private ConfigurableApplicationContext springContext;

    private FXMLLoader fxmlLoader;

    @FXML
    public TextField bookTitle;

    @FXML
    public TextField authorName;

    @FXML
    public TextField publicationDate;

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
        Date date = null;
        try {
             date = new SimpleDateFormat("dd/MM/yyyy").parse(publicationDate.getText());
        } catch (ParseException e) {
            System.out.println("Bad date");
            return;
        }
        if(date==null){
            System.out.println("Bad date");
            return;
        }
        Genre genreObject;
        genreObject = GenreDAO.findByName(genre.getText());
        if(genre==null){
            System.out.println("We dont know this genre.");
            return;
        }

        Book book = new Book(DataGenerator.generateId(), bookTitle.getText(), authorObject, date, genreObject);
        BookDAO.addBook(book);
        System.out.println("Book add succesfull");
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        System.out.println("Show book list");
        generateLoader();

        fxmlLoader.setLocation(getClass().getResource("/layouts/BooksList.fxml"));
        Parent rootNode = null;

        try {
            rootNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootNode, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void generateLoader(){
        if(fxmlLoader==null){
            //springContext = SpringApplication.run(App.class);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
        }
    }


}
