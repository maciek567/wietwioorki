package pl.wietwioorki.to22019.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CompleteReservation {
    private Long reservationId;
    private Book book;
    private Reader reader;
    private Date reservationStartDate;
    private Date reservationEndDate;
}
