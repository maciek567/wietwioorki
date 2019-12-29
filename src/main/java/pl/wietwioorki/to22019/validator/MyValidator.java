package pl.wietwioorki.to22019.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wietwioorki.to22019.repository.AuthorRepository;
import pl.wietwioorki.to22019.repository.BookRepository;
import pl.wietwioorki.to22019.repository.GenreRepository;
import pl.wietwioorki.to22019.repository.ReaderRepository;

@Component
public class MyValidator {
    @Autowired
    protected ReaderRepository readerRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected GenreRepository genreRepository;
//    protected ; todo: add more repos
}
