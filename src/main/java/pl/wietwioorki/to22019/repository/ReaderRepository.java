package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Reader;

import javax.transaction.Transactional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
//    Reader findByFullName(String fullName);

    @Transactional
    @Modifying
    @Query("UPDATE Reader c SET c.fullName = :fullName WHERE c.pesel = :pesel")
    void updateFullName(@Param("pesel") long pesel, @Param("fullName") String fullName);
}
