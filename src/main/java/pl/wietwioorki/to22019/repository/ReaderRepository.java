package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
//    Reader findByFullName(String fullName);
}
