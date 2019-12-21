package pl.wietwioorki.to22019.dao;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import pl.wietwioorki.to22019.model.Reader;
import pl.wietwioorki.to22019.model.Reservation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReservationDAO {
    private static List<Reservation> reservations = new ArrayList<>();

    public static void addReservation(Reservation reservation){
        reservations.add(reservation);
        System.out.println(reservation);
    }

    public static ObservableList<Reservation> getReservationsObservable(){
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

    public static Reservation findById(Long id) {
        for (Reservation reservation: reservations) {
            if(reservation.getReservationId().equals(id)){
                return reservation;
            }
        }
        return null;
    }

    public static List<Reservation> findByReader(Reader reader){
        List<Reservation> result = new LinkedList<>();
        for (Reservation reservation: reservations){
            if(reservation.getReader().equals(reader)){
                result.add(reservation);
            }
        }
        return result;
    }

    public static void removeById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }
}
