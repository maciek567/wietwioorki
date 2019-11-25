package pl.wietwioorki.to2019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
//@EnableAutoConfiguration
public class HomeSceneController {

    private Stage primaryStage;

    private FXMLLoader fxmlLoader;

    @FXML
    private Button reader;

    @FXML
    private Button book;

//    public HomeSceneController(Stage primaryStage, FXMLLoader loader) {
//        this.primaryStage = primaryStage;
 //         this.fxmlLoader = loader;
//    }

    @FXML
    public void handleNewReader(ActionEvent actionEvent) {
        System.out.println("New reader view");
    }

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        System.out.println("New book view");
//        fxmlLoader.setLocation(getClass().getResource("/layouts/AddBook.fxml"));
//        Parent rootNode = null;
//
//        try {
//            rootNode = fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Scene scene = new Scene(rootNode, 800, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

}
