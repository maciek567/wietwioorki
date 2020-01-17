package pl.wietwioorki.to22019.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "complete_reservation")
public class CompleteReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "pesel",
            referencedColumnName = "pesel")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_id",
            referencedColumnName = "book_id")
    private Book book;

    @Column(name = "start_date")
    private Date reservationStartDate;

    @Column(name = "was_overdue")
    @Convert(converter = BooleanConverter.class)
    private Boolean wasOverdue;

    public CompleteReservation(Reader reader, Book book, Date reservationStartDate, boolean wasOverdue) {
        this.reader = reader;
        this.book = book;
        this.reservationStartDate = reservationStartDate;
        this.wasOverdue = wasOverdue;
    }

    public CompleteReservation(Reservation reservation, boolean overdue) {
        this.reader = reservation.getReader();
        this.book = reservation.getBook();
        this.reservationStartDate = reservation.getReservationStartDate();
        this.wasOverdue = overdue;
    }

    public ObjectProperty<Long> getReservationIdProperty() {
        return new SimpleObjectProperty<>(reservationId);
    }

    public ObjectProperty<Long> getReaderPeselProperty() {
        if (reader == null) {
//            System.out.println("Zaloguj się na swoje konto, aby wypożyczyć książkę!");
        }
        return new SimpleObjectProperty<>(reader == null ? -1L : reader.getPesel());
    }

    public StringProperty getReaderNameProperty() {
        if (reader == null) {
//            System.out.println("Zaloguj się na swoje konto, aby wypożyczyć książkę!");
        }
        return new SimpleStringProperty(reader == null ? "" : reader.getFullName());
    }

    public StringProperty getBooksTitleProperty() {
        return new SimpleStringProperty(book.getTitle());
    }

    public BooleanProperty getWasOverdueProperty() {
        return new SimpleBooleanProperty(wasOverdue);
    }

    public ObjectProperty<Date> getBorrowingDateProperty() {
        return new SimpleObjectProperty<>(reservationStartDate);
    }
}
