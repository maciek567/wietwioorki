package pl.wietwioorki.to22019.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Fine;
import pl.wietwioorki.to22019.model.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class FineListController extends AbstractWindowController {
    enum StatusFilterValue {
        notPayed, payed, any
    }

    @FXML
    public Button cancelFine;

    @FXML
    public Button payFine;

    @FXML
    private TableView<Fine> fineTable;

    @FXML
    private TableColumn<Fine, Long> fineId;

    @FXML
    private TableColumn<Fine, Long> readerPesel;

    @FXML
    private TableColumn<Fine, String> description;

    @FXML
    private TableColumn<Fine, Float> amount;

    @FXML
    private TableColumn<Fine, Boolean> payed;

    @FXML
    private Text peselText;

    @FXML
    private TextField peselField;

    @FXML
    private ComboBox selectedStatus;

    @FXML
    private void initialize() {
        fineId.setCellValueFactory(dataValue -> dataValue.getValue().getFineIdProperty());
        readerPesel.setCellValueFactory(dataValue -> dataValue.getValue().getReaderPeselProperty());
        description.setCellValueFactory(dataValue -> dataValue.getValue().getDescriptionProperty());
        amount.setCellValueFactory(dataValue -> dataValue.getValue().getAmountProperty());
        payed.setCellValueFactory(dataValue -> dataValue.getValue().getIsPayedProperty());

        cancelFine.disableProperty().bind(Bindings.isEmpty(fineTable.getSelectionModel().getSelectedItems()));
        payFine.disableProperty().bind(Bindings.isEmpty(fineTable.getSelectionModel().getSelectedItems()));

        if (!isCurrentUserAdmin()) {
            peselField.setVisible(false);
            peselText.setVisible(false);
            cancelFine.setVisible(false);
            payFine.setVisible(false);
        }

        selectedStatus.setItems(getFilterItems());

        refreshDataInTable();
        selectedStatus.getSelectionModel().select(0);
    }

    private ObservableList getFilterItems() {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(StatusFilterValue.values()));
        return FXCollections.observableList(list);
    }

    private FilteredList<Fine> InitializeFilters() {
        FilteredList<Fine> filteredData = new FilteredList<>(getFineObservable(), p -> true);
        peselField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fine -> {
                if (isCurrentUserAdmin()) {
                    if (!(newValue == null || newValue.isEmpty() || fine.getReaderPeselProperty().getValue().toString().startsWith(newValue))) {
                        return false;
                    }
                }
                return true;
            });
        });
        FilteredList<Fine> filteredDataByStatus = new FilteredList<>(filteredData, p -> true);
        selectedStatus.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            filteredDataByStatus.setPredicate(fine -> {
                if (!selectedStatus.getSelectionModel().getSelectedItem().equals(StatusFilterValue.any)) {
                    if (selectedStatus.getSelectionModel().getSelectedItem().equals(StatusFilterValue.payed)) {
                        if (fine.getIsPayedProperty().getValue().equals(false)) {
                            return false;
                        }
                    }
                    if (selectedStatus.getSelectionModel().getSelectedItem().equals(StatusFilterValue.notPayed)) {
                        if (fine.getIsPayedProperty().getValue().equals(true)) {
                            return false;
                        }
                    }
                }
                return true;
            });
        });
        return filteredDataByStatus;
    }

    private void refreshfilters() {
        int selectedIndex = selectedStatus.getSelectionModel().getSelectedIndex();
        selectedStatus.getSelectionModel().selectNext();
        selectedStatus.getSelectionModel().select(selectedIndex);

        String text = peselField.getText();
        peselField.setText(text + " ");
        peselField.setText(text);
    }

    private void refreshDataInTable() {
        fineTable.setItems(InitializeFilters());
    }

    private ObservableList<Fine> getFineObservable() {
        List<Fine> fines = null;
        if (isCurrentUserAdmin()) {
            fines = sessionConstants.getFineRepository().findAll();
        } else {
            Reader reader = sessionConstants.getCurrentReader();
            if (reader != null) {
                fines = sessionConstants.getFineRepository().findByReader(reader);
            } else {
                fines = new ArrayList<>();
            }
        }
        List<Fine> finalFines = fines;
        return new ObservableListBase<Fine>() {
            @Override
            public Fine get(int index) {
                return finalFines.get(index);
            }

            @Override
            public int size() {
                return finalFines.size();
            }
        };
    }

    @FXML
    public void handleCancelFine(ActionEvent actionEvent) {
        if (isCurrentUserAdmin()) {
            Fine canceledFine = fineTable.getSelectionModel().getSelectedItem();
            sessionConstants.getFineRepository().delete(canceledFine);

            refreshDataInTable();
            refreshfilters();
        }
    }

    @FXML
    public void handlePayFine(ActionEvent actionEvent) {
        if (isCurrentUserAdmin()) {
            Fine actualFine = fineTable.getSelectionModel().getSelectedItem();
            actualFine.payFine();
            sessionConstants.getFineRepository().save(actualFine);

            fineTable.refresh();
            refreshfilters();
        }
    }
}
