package pl.wietwioorki.to22019.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Constants {
    User actualUser = null;

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
}
