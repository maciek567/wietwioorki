package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Getter
@Component
public class ReaderValidator extends MyValidator {
    private String specificErrorHeader = "adding reader";

    @Autowired
    PeselValidator peselValidator;

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
        System.out.println("READER: pesel validator: " + peselValidator);

        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            System.out.println("reader repository is: " + readerRepository);
            if (readerRepository.findById(Long.parseLong(pesel)).isPresent()) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselExistsErrorContent);
                return false;
            }
            return true;
        }
        return false;
    }

    //validateBirthDate //todo: remove
}
