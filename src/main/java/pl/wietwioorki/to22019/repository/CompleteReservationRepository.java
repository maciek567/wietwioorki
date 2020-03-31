package pl.wietwioorki.to22019.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.CompleteReservation;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;

import java.util.List;

@Repository
public interface CompleteReservationRepository extends JpaRepository<CompleteReservation, Long> {
    List<CompleteReservation> findByReader(Reader reader);
//    List<Reservation> findByBook(Book book);
}