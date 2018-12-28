package redaktor.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import redaktor.DAO.DAO;
import redaktor.DAO.RedaktorDAO;
import redaktor.model.Redaktor;

import java.util.List;

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
    }

    private void initializeTableView() {
        //TODO: move to some kind of factory?
//        TableColumn<Redaktor, Long> redaktorIdColumn = new TableColumn<>("Id redaktora");
//        TableColumn<Redaktor, String> imieColumn = new TableColumn<>("Imie");
//        TableColumn<Redaktor, String> nazwiskoColumn = new TableColumn<>("Nazwisko");
//        TableColumn<Redaktor, Long> sekcjaIdColumn = new TableColumn<>("Id sekcji");
        TableColumn<Redaktor, Long> redaktorIdColumn = createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, Long> sekcjaIdColumn = createColumn("Id sekcji", "sekcjaId", 50);


        redaktorTableView.getColumns().addAll(redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaIdColumn);
    }

    private <T> TableColumn<Redaktor, T> createColumn(String columnName, String fieldName, int width) {
        TableColumn<Redaktor, T> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Redaktor, T>(fieldName));

        return tableColumn;
    }
}
