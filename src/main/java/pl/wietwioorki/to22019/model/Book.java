package pl.wietwioorki.to22019.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.wietwioorki.to22019.repository.BookRepository;
import pl.wietwioorki.to22019.repository.ReservationRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "book")
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "book_id")
    private Long bookId;
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author; // todo: each book has only one author

    private Date publicationDate;

//    @ManyToMany(cascade = { CascadeType.ALL })
//    @JoinTable(
//            name = "book_genre",
//            joinColumns = { @JoinColumn(name = "book_id") },
//            inverseJoinColumns = { @JoinColumn(name = "genre_id") }
//    )
//    private Set<Genre> genres = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

//    private Queue<Reader> waitingReaders = new LinkedList<>();
    @Setter
    private Double averageRating = 0.0;
    private int votesCount = 0;

    private int noBorrows;


    public Book(String title, Author author, Date publicationDate, Genre genre) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.noBorrows = 0;
    }

    public ObjectProperty<Long> getIdProperty(){
        return new SimpleObjectProperty<>(bookId);
    }
    public StringProperty getTitleProperty(){
        return new SimpleStringProperty(title);
    }
    public StringProperty getAuthorProperty(){
        return new SimpleStringProperty(author.getFullName());
    }
    public ObjectProperty<Date> getDateProperty(){
        return new SimpleObjectProperty<>(publicationDate);
    }
    public StringProperty getGenreProperty(){
        return new SimpleStringProperty(genre.getName());
    }
    public DoubleProperty getAverageRatingProperty() {
        return new SimpleDoubleProperty(averageRating);
    }

    public void pushReaderToQueue(Reader reader) {
//        this.waitingReaders.add(reader);
    }

    public Reader popReaderFromQueue() {
//       return this.waitingReaders.remove();
        return null;
    }

    public Reader checkNextReservation(ReservationRepository reservationRepo) {
        List<Reservation> reservations = reservationRepo.findByBook(this);
        if(!reservations.isEmpty()){
            Reservation reservation = reservations.get(0);
            reservation.setReservationStatus(ReservationStatus.READY);
            reservationRepo.save(reservation);
        }
        return null;
    }

    public boolean isReaderQueueEmpty(ReservationRepository reservationRepo) {
        List<Reservation> reservations = reservationRepo.findByBook(this);
        return reservations.isEmpty();
//        return this.waitingReaders.isEmpty();
    }

    public int getReaderQueueSize() {
        return 0;
//        return waitingReaders.size();
    }

    public void incrementVotesCount() {
        this.votesCount++;
    }

    public void incrementNoBorrows() {
        this.noBorrows++;
    }


    @Override
    public int compareTo(Book o) {
        Double result = o.getAverageRating() * o.getVotesCount() - this.averageRating * this.votesCount;
        return result.intValue();
    }

}
