package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByReader(Reader reader);

    User findByLogin(String login);

    @Transactional
    @Modifying
    @Query("UPDATE User c SET c.email = :email WHERE c.login = :login")
    void updateEmail(@Param("login") String login, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User c SET c.password = :password WHERE c.login = :login")
    void updatePassword(@Param("login") String login, @Param("password") String password);
}