package pl.wietwioorki.to22019.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString
public class Reservation {
    private Long reservationId;
    private Reader reader;
    private Book book;
    private Date reservationStartDate;
    private Date reservationEndDate;
    @Setter
    private ReservationStatus reservationStatus;

    public Reservation(Reader reader, Book book, Date reservationStartDate, Date reservationEndDate, ReservationStatus reservationStatus) {
        this.reader = reader;
        this.book = book;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.reservationStatus = reservationStatus;
    }

    public ObjectProperty<Long> getReservationIdProperty(){
        return new SimpleObjectProperty<>(reservationId);
    }
    public ObjectProperty<Long> getReaderPeselProperty(){
        if(reader == null) {
            System.out.println("Zaloguj się na swoje konto, aby wypożyczyć książkę!");
        }
        return new SimpleObjectProperty<>(reader == null ? -1L : reader.getPesel());
    }
    public StringProperty getReaderNameProperty(){
        if(reader == null) {
            System.out.println("Zaloguj się na swoje konto, aby wypożyczyć książkę!");
        }
        return new SimpleStringProperty(reader == null ? "": reader.getFullName());
    }
    public StringProperty getBooksTitleProperty(){
        return new SimpleStringProperty(book.getTitle());
    }
    public  ObjectProperty<ReservationStatus> getReservationStatusProperty() {return new SimpleObjectProperty<>(reservationStatus); }
    public ObjectProperty<Date> getBorrowingDateProperty(){ return new SimpleObjectProperty<>(reservationStartDate); }
    public ObjectProperty<Date> getReturnDateProperty(){ return new SimpleObjectProperty<>(reservationEndDate); }

    public void borrowBook(){
        book.popReaderFromQueue();
        reservationStartDate = new Date(System.currentTimeMillis());
    }

    public void returnBook(){
        reservationEndDate = new Date(System.currentTimeMillis());
    }

    // fixme - to discuss - how long should be borrowing time?
    public static int getBorrowingTimeInDays(){ return 14; }

    public void setReservationStartDate(Date reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public void setReservationEndDate(Date reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

}
