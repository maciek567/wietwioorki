package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeSceneController {

    @Setter  // @Autowired @Qualifier("primaryStage")
    private static Stage primaryStage;

    @Autowired
    private ConfigurableApplicationContext springContext;

    private FXMLLoader fxmlLoader;

    @FXML
    private Button addReaderButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button showBookListButton;


    @FXML
    public void handleNewReader(ActionEvent actionEvent) {
        System.out.println("New reader view");
        loadScene("/layouts/AddReader.fxml");
        AddReaderController.setPrimaryStage(primaryStage);
    }

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        System.out.println("New book view");
        loadScene("/layouts/AddBook.fxml");
        AddBookController.setPrimaryStage(primaryStage);
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        System.out.println("Show book list");
        loadScene("/layouts/BooksList.fxml");
        BooksListController.setPrimaryStage(primaryStage);
    }

    private void loadScene(String layoutPath){
        if(fxmlLoader==null){
            //springContext = SpringApplication.run(App.class);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
        }

        fxmlLoader.setLocation(getClass().getResource(layoutPath));
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

}
