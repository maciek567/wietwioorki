package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByReader(Reader reader);

    User findByLogin(String login);
}