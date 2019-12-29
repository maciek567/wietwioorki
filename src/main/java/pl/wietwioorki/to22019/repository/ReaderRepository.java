package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Reader;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findByFullName(String fullName);
}
