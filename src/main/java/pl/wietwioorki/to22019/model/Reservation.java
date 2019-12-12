package pl.wietwioorki.to22019.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class Reservation {
    private Long reservationId;
    private Reader reader;
    private Book book;
    private Date borrowingDate;
    private Date returnDate;


    public ObjectProperty<Long> getReservationIdProperty(){
        return new SimpleObjectProperty<>(reservationId);
    }
    public ObjectProperty<Long> getReaderPeselProperty(){ return new SimpleObjectProperty<>(reader.getPesel()); }
    public StringProperty getReaderNameProperty(){
        return new SimpleStringProperty(reader.getFullName());
    }
    public StringProperty getBooksTittleProperty(){
        return new SimpleStringProperty(book.getTitle());
    }
    public ObjectProperty<Date> getBorrowingDateProperty(){
        return new SimpleObjectProperty<Date>(borrowingDate);
    }
    public ObjectProperty<Date> getReturnDateProperty(){
        return new SimpleObjectProperty<Date>(returnDate);
    }
}
