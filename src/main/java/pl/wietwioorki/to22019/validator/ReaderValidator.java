package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

public class ReaderValidator {

    public boolean validateNames(String name, String surname) {
        if (name.isBlank() || surname.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", "name" + emptyFieldErrorContent);
            return false;
        } else if (!StringUtils.isAlpha(name) || !StringUtils.isAlpha(name)){
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", wrongNamesErrorContent);
            return false;
        }
            return true;
    }

    public boolean validatePesel(String pesel) {
        if (pesel.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", "pesel" + emptyFieldErrorContent);
            return false;
        }
        if (pesel.length() != 11 || !StringUtils.isNumeric(pesel)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", wrongPeselErrorContent);
            return false;
        }
        if (ReaderDAO.findByPesel(Long.parseLong(pesel)) != null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "adding reader", readerWithGivenPeselExistsErrorContent);
            return false;
        }
        return true;
    }

    //validateBirthDate //todo: remove
}
