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

@AllArgsConstructor
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
        return new SimpleObjectProperty<>(reservationStartDate);
    }
    public ObjectProperty<Date> getReturnDateProperty(){
        return new SimpleObjectProperty<Date>(reservationEndDate);
    }

    public void borrowBook(){
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
