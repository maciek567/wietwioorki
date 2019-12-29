package pl.wietwioorki.to22019.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.repository.ReaderRepository;

@Component
public class MyValidator {
    @Autowired
    protected ReaderRepository readerRepository;
//    protected ; todo: add more repos
}
