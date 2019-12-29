package pl.wietwioorki.to22019.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AlertFactory {
    public static Alert createAlert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        if (alertType == Alert.AlertType.CONFIRMATION) {
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);
        }
        return alert;
    }

    public static void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
        AlertFactory.createAlert(alertType, headerText, contentText).show();
    }
}
