package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.wietwioorki.to22019.repository.ReaderRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Optional;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;

    private String login;
    private String password;
    private Role role;
    private String email;
    private Long pesel; //todo: change to user

    @Transient //@Autowired
    ReaderRepository readerRepository;

    public Optional<Reader> getReader() {
        return readerRepository.findById(pesel); // todo: remove ReaderDAO
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
