package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class Reservation {
    private Long reservationId;
    private Book book;
    private Reader reader;
    private Date reservationDate;
    private ReservationStatus status;

    public void changeStatusTo(ReservationStatus status) {
        this.status = status;
    }
}
