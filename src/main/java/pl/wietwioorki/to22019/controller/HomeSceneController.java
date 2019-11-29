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
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.App;

import java.io.IOException;

@Controller
//@Component
public class HomeSceneController {


  //  @Autowired @Qualifier("primaryStage")
    @Setter
    private static Stage primaryStage;


    @Autowired
    private ConfigurableApplicationContext springContext;

    private FXMLLoader fxmlLoader;

    @FXML
    private Button addReaderButton;

    @FXML
    private Button addBookButton;

/*
public HomeSceneController(Stage primaryStage, FXMLLoader loader) {
this.primaryStage = primaryStage;
this.fxmlLoader = loader;
}
*/

    @FXML
    public void handleNewReader(ActionEvent actionEvent) {
        System.out.println("New reader view");
        generateLoader();

        fxmlLoader.setLocation(getClass().getResource("/layouts/AddReader.fxml"));
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

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        System.out.println("New book view");
        generateLoader();

        fxmlLoader.setLocation(getClass().getResource("/layouts/AddBook.fxml"));
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
