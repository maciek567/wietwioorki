package pl.wietwioorki.to22019;

import org.springframework.context.annotation.Configuration;
import pl.wietwioorki.to22019.model.User;

@Configuration
public class SessionConfig {

    private static User user = null;

    public static User getUser(){
        return user;
    }

    public static void logUser(User logUser){
        user = logUser;
    }
}
