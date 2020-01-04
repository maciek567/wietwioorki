package pl.wietwioorki.to22019.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.util.SessionConstants;

@Component
public class MyValidator {
    @Autowired
    SessionConstants sessionConstants;
}
