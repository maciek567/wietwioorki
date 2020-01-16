package pl.wietwioorki.to22019.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.wietwioorki.to22019.model.Book;

public class RecommendationsController {

    @FXML
    private TextField userName;

    @FXML
    private TableView<Book> personalizedMetricTable;

    @FXML
    private TableView<Book> mostPopularMetricTable;

    @FXML
    private TableView<Book> recentlyPopularMetricTable;


}
