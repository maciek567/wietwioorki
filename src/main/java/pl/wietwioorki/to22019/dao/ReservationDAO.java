package pl.wietwioorki.to22019.dao;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import pl.wietwioorki.to22019.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static List<Reservation> reservations = new ArrayList<>();

    public static void addReservation(Reservation reservation){
        reservations.add(reservation);
        System.out.println(reservation);
    }

    public static ObservableList<Reservation> getBooksObservable(){
        return new ObservableListBase<Reservation>() {
            @Override
            public Reservation get(int index) {
                return reservations.get(index);
            }

            @Override
            public int size() {
                return reservations.size();
            }
        };
    }
}
