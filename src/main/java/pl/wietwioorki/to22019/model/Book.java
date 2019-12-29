package pl.wietwioorki.to22019.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Book(String title, Author author, Date publicationDate, Genre genre) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
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

    public boolean isReaderQueueEmpty() {
        return true;
//        return this.waitingReaders.isEmpty();
    }

    public int getReaderQueueSize() {
        return 0;
//        return waitingReaders.size();
    }

    public void incrementVotesCount() {
        this.votesCount++;
    }
}
