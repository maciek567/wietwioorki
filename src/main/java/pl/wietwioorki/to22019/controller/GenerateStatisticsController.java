package pl.wietwioorki.to22019.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;

import java.util.List;

@Controller
public class GenerateStatisticsController extends AbstractWindowController {

    @FXML
    public Button generateStatisticsButton;

    @FXML
    private Text mostBorrowedBookTittle;

    @FXML
    private Text mostBorrowedBookBorrows;

    @FXML
    public void handleGenerateStatistics(ActionEvent actionEvent) {
        System.out.println("generating statistics");
        Book mostBorrowedBook = theMostBorrowedBook();
        mostBorrowedBookTittle.setText(mostBorrowedBook != null ? mostBorrowedBook.getTitle() : "no book in library");
        mostBorrowedBookBorrows.setText(String.valueOf(mostBorrowedBook != null ? mostBorrowedBook.getNoBorrows() : 0));
    }

    private Book theMostBorrowedBook() {
        List<Book> books = sessionConstants.getBookRepository().findAll();
        if(books.isEmpty()) {
            System.out.println("No book in library system");
            return null;
        }
        Book mostBorrowedBook = books.get(0);
        for(Book book : books) {
            System.out.println(book.getTitle() + " " + book.getNoBorrows());
            if(book.getNoBorrows() > mostBorrowedBook.getNoBorrows()) {
                mostBorrowedBook = book;
            }
        }
        return mostBorrowedBook;
    }

}
