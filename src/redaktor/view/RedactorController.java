package redaktor.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import redaktor.DAO.DAO;
import redaktor.DAO.RedaktorDAO;
import redaktor.model.Redaktor;

import java.util.List;
import java.util.Observable;

public class RedactorController {

    private DAO<Redaktor> redaktorDAO;

    @FXML
    private TableView<Redaktor> redaktorTableView;

    @FXML
    public void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();

        initializeTableView();
    }

    @FXML
    public void showRedactors() {
        List<Redaktor> redactors = redaktorDAO.getAll();
        ObservableList<Redaktor> redaktorObservableList = createRedactorsObservableList(redactors);

        System.out.println("showRedactors Clicked");

        redaktorTableView.setItems(redaktorObservableList);
    }

    private void initializeTableView() {
        //TODO: move to some kind of factory?
        TableColumn<Redaktor, Long> redaktorIdColumn = createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaIdColumn = createColumn("Nazwa sekcji", "sekcjaNazwa", 50);

        redaktorTableView.getColumns().addAll(redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaIdColumn);
    }

    private <T> TableColumn<Redaktor, T> createColumn(String columnName, String fieldName, int width) {
        TableColumn<Redaktor, T> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Redaktor, T>(fieldName));

        return tableColumn;
    }

    private ObservableList<Redaktor> createRedactorsObservableList(List<Redaktor> redactors) {
        ObservableList<Redaktor> redactorsObservable = FXCollections.observableList(redactors);

        return redactorsObservable;
    }
}
