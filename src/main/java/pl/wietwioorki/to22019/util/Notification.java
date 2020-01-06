package pl.wietwioorki.to22019.util;

import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;

import java.util.List;

public class Notification {

    public static String formNotification(List<Reservation> reservations) {
        StringBuilder contentText = new StringBuilder();
        for (Reservation reservation : reservations) {
            if(reservation.getReservationStatus() != ReservationStatus.RETURNED) {
                contentText.append(reservation.getBook().getTitle());
                contentText.append(": ");
                contentText.append(reservation.getReservationStatus().toString());
                contentText.append("\n");
            }
        }
        contentText.append("\n*********************************************************\n");
        contentText.append("PENDING - book is already taken by someone else - we will notify you when it will be available\n");
        contentText.append("READY - Book is available! Go to library to borrow it\n");
        contentText.append("ACTIVE - You have borrowed this book - please remember to return it on time\n");
        contentText.append("OVERDUE - Time of borrowing has ended - please return the book immediately\n");
        return contentText.toString();
    }

}
