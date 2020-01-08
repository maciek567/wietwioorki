package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Role;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.util.SessionConstants;

import java.io.IOException;

import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public abstract class AbstractWindowController {
    @Autowired
    protected SessionConstants sessionConstants;

    @Autowired
    private ConfigurableApplicationContext springContext;

    public void openNewWindow(String layoutPath) {
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
        rootNode.requestFocus();
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
            }
        });
        primaryStage.showAndWait();
    }

    public void closeWindowAfterSuccessfulAction(ActionEvent actionEvent) {
        final Stage stage = (Stage) (((Node) (actionEvent.getSource())).getScene().getWindow());
        stage.close();
    }

    public boolean isCurrentUserAdmin() {
        if (sessionConstants.getCurrentUser() != null) {
            return sessionConstants.getCurrentUser().getRole().equals(Role.L);
        }
        return false;
    }

    public boolean isCurrentUserGuest() {
        if (sessionConstants.getCurrentUser() != null) {
            return sessionConstants.getCurrentUser().getRole().equals(Role.G);
        }
        return true;
    }

    public void showLogInNeededAlert() {
        AlertFactory.showAlert(Alert.AlertType.WARNING, loggedAsGuestHeader, loggedAsGuestContent);
    }

    public void showAdministratorNeededAlert() {
        AlertFactory.showAlert(Alert.AlertType.WARNING, adminNeededHeader, adminNeededContent);
    }
}
