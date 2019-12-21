package pl.wietwioorki.to22019.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import pl.wietwioorki.to22019.model.Constants;

import java.io.IOException;

public abstract class AbstractWindowController {
    @Autowired
    protected Constants constants;

    @Autowired
    private ConfigurableApplicationContext springContext;

    public void openNewWindow(String layoutPath){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);

        fxmlLoader.setLocation(getClass().getResource(layoutPath));
        Parent rootNode = null;

        try {
            rootNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\n\n\nProblem z fxml.load\n\n");
        }

        Scene scene = new Scene(rootNode);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(480);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
        rootNode.requestFocus();
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
            }
        });
    }
}