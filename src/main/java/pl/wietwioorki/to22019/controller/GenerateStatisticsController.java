package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.User;

import java.util.List;

@Controller
public class GenerateStatisticsController extends AbstractWindowController {

    @FXML
    public Button generateStatisticsButton;

    @FXML
    private Text mostBorrowedBookTittle;
    @FXML
    private Text mostBorrowedBookBorrowings;

    @FXML
    public Text mostLoggedInUserName;
    @FXML
    public Text mostLoggedInUserLogins;

    @FXML
    public Text userWithMostBorrowingsName;
    @FXML
    public Text userWithMostBorrowingsBorrowings;

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
}
