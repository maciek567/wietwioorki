package pl.wietwioorki.to22019.model;

import javafx.scene.control.DatePicker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@Getter
@Setter
public class Constants {
    User actualUser = null;

    public void logUser(User user){
        actualUser = user;
    }

    public String getUserLogin(){
        String name = "";
        if(actualUser!=null){
            name = actualUser.getLogin();
        }
        return name;
    }

    public Reader getActualReader(){
        Reader reader = null;
        if(actualUser!=null){
            reader = actualUser.getReader();
        }
        return reader;
    }

    public Date datePickerConverter(DatePicker datePicker) throws ParseException {
        // workaround to enable manual (using keyboard) date changing (from https://bugs.openjdk.java.net/browse/JDK-8144499)
        DatePicker newDatePicker = new DatePicker(datePicker.getConverter().fromString(datePicker.getEditor().getText()));

        String formatted = newDatePicker.getValue().format((DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(formatted);
    }
}
