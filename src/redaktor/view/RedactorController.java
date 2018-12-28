package redaktor.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import redaktor.DAO.DAO;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.List;

public class RedactorController {

    private DAO<Redaktor> redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    @FXML
    private TableView<Redaktor> redaktorTableView;
    
    @FXML
    private ChoiceBox<String> sekcjaChoiceBox;

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    public void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = (SekcjaDAO)SekcjaDAO.getInstance();

        initializeRedaktorTableView();
        updateSekcjaChoiceBox();
    }

    @FXML
    public void addRedactor() {
        String sekcjaNazwa = sekcjaChoiceBox.getValue();
        String imie = imieTextField.getText();
        String nazwisko =  nazwiskoTextField.getText();

        Sekcja sekcja = sekcjaDAO.getByNazwa(sekcjaNazwa);
        long sekcjaId = sekcja.getSekcjaId();

        Redaktor redaktor = new Redaktor(0, imie, nazwisko, sekcjaId);
        redaktorDAO.save(redaktor);
    }

    @FXML
    //TODO: modify name to update
    public void showRedactors() {
        List<Redaktor> redactors = redaktorDAO.getAll();
        ObservableList<Redaktor> redaktorObservableList = createObservableListFromList(redactors);

        System.out.println("showRedactors Clicked");

        redaktorTableView.setItems(redaktorObservableList);
    }

    private void initializeRedaktorTableView() {
        //TODO: move to some kind of factory?
        TableColumn<Redaktor, Long> redaktorIdColumn = createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = createColumn("Nazwa sekcji", "sekcjaNazwa", 50);

        redaktorTableView.getColumns().addAll(redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
    }

    private <T> TableColumn<Redaktor, T> createColumn(String columnName, String fieldName, int width) {
        TableColumn<Redaktor, T> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Redaktor, T>(fieldName));

        return tableColumn;
    }

    private void updateSekcjaChoiceBox() {
        List<String> sections = sekcjaDAO.getAllNazwa();
        ObservableList<String> sekcjaNazwaObservableList = createObservableListFromList(sections);

        System.out.println("showRedactors Clicked");

        sekcjaChoiceBox.setItems(sekcjaNazwaObservableList);
    }

    private <T> ObservableList<T> createObservableListFromList(List<T> list) {
        ObservableList<T> observableList = FXCollections.observableList(list);

        return observableList;
    }

}
