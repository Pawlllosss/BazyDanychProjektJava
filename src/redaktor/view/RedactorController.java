package redaktor.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import redaktor.DAO.DAO;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.List;

//TODO: create tabs
//TODO: refresh redactor list automatically

public class RedactorController {

    private DAO<Redaktor> redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    @FXML
    private TableView<Redaktor> redaktorTableView;
    
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    public void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = (SekcjaDAO)SekcjaDAO.getInstance();

        sekcjaChoiceBox.setConverter(new StringConverter<Sekcja>() {
            @Override
            public String toString(Sekcja sekcja) {
                return sekcja.getNazwa();
            }

            @Override
            public Sekcja fromString(String s) {
                return null;
            }
        });

        initializeRedaktorTableView();
        updateSekcjaChoiceBox();
    }

    @FXML
    public void addRedactor() {
        //TODO: add some field validation and maybe triggers?
        Sekcja sekcja = sekcjaChoiceBox.getValue();
        String imie = imieTextField.getText();
        String nazwisko =  nazwiskoTextField.getText();

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
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");
        sekcjaNazwaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Redaktor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Redaktor, String> redaktorStringCellDataFeatures) {
                Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
                Sekcja sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
                String sekcjaNazwa = sekcja.getNazwa();

                return new SimpleStringProperty(sekcjaNazwa);
            }
        });

        redaktorTableView.getColumns().addAll(redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
    }

    private <T> TableColumn<Redaktor, T> createColumn(String columnName, String fieldName, int width) {
        TableColumn<Redaktor, T> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<Redaktor, T>(fieldName));

        return tableColumn;
    }

    private void updateSekcjaChoiceBox() {
//        List<Sekcja> sections = sekcjaDAO.getAllNazwa();
//        ObservableList<String> sekcjaNazwaObservableList = createObservableListFromList(sections);

        List<Sekcja> sections = sekcjaDAO.getAll();
        ObservableList<Sekcja> sekcjaNazwaObservableList = createObservableListFromList(sections);

        System.out.println("showRedactors Clicked");

        sekcjaChoiceBox.setItems(sekcjaNazwaObservableList);
    }

    private <T> ObservableList<T> createObservableListFromList(List<T> list) {
        ObservableList<T> observableList = FXCollections.observableList(list);

        return observableList;
    }

}
