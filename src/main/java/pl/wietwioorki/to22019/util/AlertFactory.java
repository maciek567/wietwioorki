package pl.wietwioorki.to22019.util;

import javafx.scene.control.Alert;

public class AlertFactory {
    private static Alert createAlert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

    public static void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
        AlertFactory.createAlert(alertType, headerText, contentText).show();
    }
}
