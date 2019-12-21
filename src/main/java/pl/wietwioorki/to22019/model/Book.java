package pl.wietwioorki.to22019.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Queue;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    private Long bookId;
    private String title;
    private Author author;
    private Date publicationDate;
    private Genre genre;
    private Queue<Reader> waitingReaders;
    private Double averageRating;
    private int votesCount;

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

    public void pushReaderToQueue(Reader reader) {
        this.waitingReaders.add(reader);
    }

    public Reader popReaderFromQueue() {
       return this.waitingReaders.remove();
    }

    public boolean isEmpty() {
        return this.waitingReaders.isEmpty();
    }

    public void incrementVotesCount() {
        this.votesCount++;
    }
}
