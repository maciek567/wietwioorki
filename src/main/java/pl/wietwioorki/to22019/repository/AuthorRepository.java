package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByFullName(String fullName);
}
