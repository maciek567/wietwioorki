package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

public class PeselValidator {
    public boolean validate(String pesel, String specificErrorHeader) {
        if (pesel.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "Pesel" + emptyFieldErrorContent);
            return false;
        }
        if (pesel.length() != 11 || !StringUtils.isNumeric(pesel)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongPeselErrorContent);
            return false;
        }
        return true;
    }
}
