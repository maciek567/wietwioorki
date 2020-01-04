package pl.wietwioorki.to22019.validator;

import javafx.scene.control.Alert;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.ErrorMessage.*;

@Getter
@Component
public class RegistrationValidator extends MyValidator {
    private String specificErrorHeader = "registering reader";

    @Autowired
    PeselValidator peselValidator;

    public boolean validateName(String name) {
        if (name.isBlank()) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, "name" + emptyFieldErrorContent);
            return false;
        }
        String[] names = name.split(" ");
        if (names.length != 2 || !StringUtils.isAlpha(names[0]) || !StringUtils.isAlpha(names[1])) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongNamesErrorContent);
            return false;
        }
        return true;
    }

    public boolean validatePesel(String pesel) {
        if (new PeselValidator().validate(pesel, specificErrorHeader)) {
            if (readerRepository.findById(Long.parseLong(pesel)).isPresent()) {
                AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, readerWithGivenPeselExistsErrorContent);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean validateUser(String login) {
        if (userRepository.findByLogin(login) != null) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, userWithGivenPeselExistsErrorContent);
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongEmailErrorContent);
            return false;
        }
        return true;
    }

    public boolean validatePasswords(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + specificErrorHeader, wrongPasswordsErrorContent);
            return false;
        }
        return true;
    }
}
