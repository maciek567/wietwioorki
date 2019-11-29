package pl.wietwioorki.to22019;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.wietwioorki.to22019.controller.HomeSceneController;


@SpringBootApplication
public class App extends Application {

    @Autowired
    private ConfigurableApplicationContext springContext;

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Library system");

        fxmlLoader.setLocation(getClass().getResource("/layouts/HomeScene.fxml"));
        Parent rootNode = null;

        rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        HomeSceneController.setPrimaryStage(primaryStage);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(App.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
