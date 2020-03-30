package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class GenerateStatisticsController extends AbstractWindowController {

    @FXML
    private Button generateStatisticsButton;

    @FXML
    private Text mostBorrowedBookTittle;
    @FXML
    private Text mostBorrowedBookBorrowings;

    @FXML
    private Text mostLoggedInUserName;
    @FXML
    private Text mostLoggedInUserLogins;

    @FXML
    private Text userWithMostBorrowingsName;
    @FXML
    private Text userWithMostBorrowingsBorrowings;

    @FXML
    private Text noUsers;
    @FXML
    private Text noBooks;
    @FXML
    private Text noReservations;
    @FXML
    private Text noAuthors;
    @FXML
    private Text noGenres;

    @FXML
    private LineChart<String, Long> lineChart;

    @FXML
    public void handleGenerateStatistics(ActionEvent actionEvent) throws IOException {
        System.out.println("generating statistics");

        Book mostBorrowedBook = theMostBorrowedBook();
        mostBorrowedBookTittle.setText(mostBorrowedBook != null ? mostBorrowedBook.getTitle() : "no book in library");
        mostBorrowedBookBorrowings.setText(String.valueOf(mostBorrowedBook != null ? mostBorrowedBook.getNoBorrows() : 0));

        User mostLoggedInUser = theMostFrequentlyLoggedInUser();
        mostLoggedInUserName.setText(mostLoggedInUser != null ? mostLoggedInUser.getLogin() : "no user in library");
        mostLoggedInUserLogins.setText(String.valueOf(mostLoggedInUser != null ? mostLoggedInUser.getNoLogins() : 0));

        User mostBorrowingsUser = userWithTheMostBorrowings();
        userWithMostBorrowingsName.setText(mostBorrowingsUser != null ? mostBorrowingsUser.getLogin() : "no user in library");
        userWithMostBorrowingsBorrowings.setText(String.valueOf(mostBorrowingsUser != null ? mostBorrowingsUser.getNoBorrowings() : 0));

        noUsers.setText(String.valueOf(getNoUsers()));
        noBooks.setText(String.valueOf(getNoBooks()));
        noReservations.setText(String.valueOf(getNoReservations()));
        noAuthors.setText(String.valueOf(getNoAuthors()));
        noGenres.setText(String.valueOf(getNoGenres()));

        showChart();
    }

    private Book theMostBorrowedBook() {
        List<Book> books = sessionConstants.getBookRepository().findAll();
        if(books.isEmpty()) {
            System.out.println("No book in library system");
            return null;
        }
        Book mostBorrowedBook = books.get(0);
        for(Book book : books) {
            if(book.getNoBorrows() > mostBorrowedBook.getNoBorrows()) {
                mostBorrowedBook = book;
            }
        }
        return mostBorrowedBook;
    }

    private User theMostFrequentlyLoggedInUser() {
        List<User> users = sessionConstants.getUserRepository().findAll();
        if(users.isEmpty()) {
            System.out.println("No user in library system");
            return null;
        }
        User mostLoggedInUser = users.get(0);
        for(User user : users) {
            if(user.getNoLogins() > mostLoggedInUser.getNoLogins()) {
                mostLoggedInUser = user;
            }
        }
        return mostLoggedInUser;
    }

    private User userWithTheMostBorrowings() {
        List<User> users = sessionConstants.getUserRepository().findAll();
        if(users.isEmpty()) {
            System.out.println("No user in library system");
            return null;
        }
        User userWithMostBorrowings = users.get(0);
        for(User user : users) {
            if(user.getNoBorrowings() > userWithMostBorrowings.getNoBorrowings()) {
                userWithMostBorrowings = user;
            }
        }
        return userWithMostBorrowings;
    }

    private int getNoBooks() {
        return sessionConstants.getBookRepository().findAll().size();
    }
    private int getNoUsers() {
        return sessionConstants.getUserRepository().findAll().size();
    }

    private int getNoReservations() {
        return sessionConstants.getReservationRepository().findAll().size();
    }

    private int getNoAuthors() {
        return sessionConstants.getAuthorRepository().findAll().size();
    }

    private int getNoGenres() {
        return sessionConstants.getGenreRepository().findAll().size();
    }


    private Map<Date, Long> getCountedReservations() throws IOException {
        // current and all completed reservations
        List<Reservation> reservations = sessionConstants.getReservationRepository().findAll();
        List<CompleteReservation> completeReservations = sessionConstants.getCompleteReservationRepository().findAll();
        for(CompleteReservation complete : completeReservations ) {
            Reservation reservation = new Reservation(complete.getReader(), complete.getBook(),
                    complete.getReservationStartDate(), null, ReservationStatus.RETURNED);
            reservations.add(reservation);
        }


        // stream data and filter borrowed books from last month
        Stream<Reservation> reservationStream =  reservations.stream();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();
        List<Reservation> filteredReservations = reservationStream
                .filter(reservation -> !reservation.getReservationStatus().equals(ReservationStatus.PENDING))
                .filter(reservation -> !reservation.getReservationStatus().equals(ReservationStatus.READY))
                .filter(reservation -> Objects.nonNull(reservation.getReservationStartDate()))
                .filter(reservation -> reservation.getReservationStartDate().after(oneMonthAgo))
                .collect(Collectors.toList());

        // set hoers, minutes and seconds to 0 so that reservations with the same day are treated identically
        // group by reservation day and count reservations in every day
        Map<Date, Long> reservationsGrouped = filteredReservations.stream()
                .map(reservation -> {
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(reservation.getReservationStartDate());
                    cal2.set(Calendar.HOUR_OF_DAY, 0);
                    cal2.set(Calendar.MINUTE, 0);
                    cal2.set(Calendar.SECOND, 0);
                    cal2.set(Calendar.MILLISECOND, 0);
                    reservation.setReservationStartDate(cal2.getTime());
                    return reservation;
                })
                .collect(Collectors.groupingBy(Reservation::getReservationStartDate, Collectors.counting()));


        // insert 0 if in certain day nobody reserved any book
        for(int i=0; i<=30; i++) {
            reservationsGrouped.putIfAbsent(cal.getTime(), 0L);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        for(Map.Entry<Date, Long> reservationCount : reservationsGrouped.entrySet()) {
            System.out.println(reservationCount.getKey() + " " + reservationCount.getValue());
        }

        // sort by day (treeMap do it automatically)
        return new TreeMap<>(reservationsGrouped);
    }

   //  display line chart
    private void showChart() {
        XYChart.Series<String, Long> series = new XYChart.Series<>();
        Calendar calendar = Calendar.getInstance();
        try {
            for(Map.Entry<Date, Long> reservationCount : getCountedReservations().entrySet()) {
                calendar.setTime(reservationCount.getKey());
                String dayName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                series.getData().add(new XYChart.Data<>(calendar.get(Calendar.DAY_OF_MONTH)
                        + " " + dayName, reservationCount.getValue()));

            }
            lineChart.getData().add(series);
        } catch(RuntimeException | IOException e) {
            System.out.println("Wrong data in line chart");
            System.out.println(e.getMessage());
        }
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);
    }

}
