package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.*;

import java.util.List;

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
    public void handleGenerateStatistics(ActionEvent actionEvent) {
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
}
