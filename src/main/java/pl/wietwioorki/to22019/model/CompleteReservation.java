package pl.wietwioorki.to22019.model;

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
    private Book book; // todo: only one book in each reservation?

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
}
