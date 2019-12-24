package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import org.apache.commons.lang3.StringUtils;
import pl.wietwioorki.to22019.dao.ReaderDAO;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

public class ReaderValidator {
    private String specificErrorHeader = "adding reader";

    public boolean validateNames(String name, String surname) {
        if (name.isBlank() || surname.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "name" + emptyFieldErrorContent);
            return false;
        } else if (!StringUtils.isAlpha(name) || !StringUtils.isAlpha(name)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongNamesErrorContent);
            return false;
        }
        return true;
    }

    public boolean validatePesel(String pesel) {
        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            if (ReaderDAO.findByPesel(Long.parseLong(pesel)) != null) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselExistsErrorContent);
                return false;
            }
            return true;
        }
        return false;
    }

    //validateBirthDate //todo: remove
}
