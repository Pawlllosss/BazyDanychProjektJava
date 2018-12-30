package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.initialize.DisplayNameRetriever;
import redaktor.initialize.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.ViewUpdater;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;


public class RedaktorzyTabController implements ValueObjectController<Redaktor> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private ObservableListWrapper<Redaktor> redaktorObservableListWrapper;

    @FXML
    private TableView<Redaktor> redaktorTableView;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    @Override
    public ObservableList<Redaktor> getObservableList() {
        return redaktorObservableListWrapper.getObservableList();
    }

    @FXML
    private void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        redaktorObservableListWrapper = new ObservableListWrapper<>(redaktorDAO);

        initializeRedaktorTableView();
        //TODO: in Java8 I could use lamba and functional features...
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, new DisplayNameRetriever<Sekcja>() {
            @Override
            public String getName(Sekcja sekcja) {
                return sekcja.getNazwa();
            }
        });

//        updateRedactorTableView();
        //TODO: remove it when sekcja will be refactored
        updateSekcjaChoiceBox();
    }

    @FXML
    private void addRedactor() {
        //TODO: add some field validation and maybe triggers?
        Sekcja sekcja = sekcjaChoiceBox.getValue();
        String imie = imieTextField.getText();
        String nazwisko =  nazwiskoTextField.getText();
        long sekcjaId = sekcja.getSekcjaId();

        Redaktor redaktor = new Redaktor(0, imie, nazwisko, sekcjaId);
        redaktorDAO.save(redaktor);
        redaktorObservableListWrapper.updateObservableList();
        //updating creates new ref(redaktorObservableListWrapper.getObservableList());

        //TODO: use observer pattern
//        updateRedactorTableView();
    }

    private void initializeRedaktorTableView() {
        TableColumn<Redaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = ViewInitializer.createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = ViewInitializer.createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        //TODO: java7 doesn't have lambdas...
        sekcjaNazwaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Redaktor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Redaktor, String> redaktorStringCellDataFeatures) {
                Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
                Sekcja sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
                String sekcjaNazwa = sekcja.getNazwa();

                return new SimpleStringProperty(sekcjaNazwa);
            }
        });

        ViewInitializer.addColumnsToTableView(redaktorTableView, redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(redaktorTableView, redaktorObservableListWrapper.getObservableList());
    }

    private void updateRedactorTableView() {
        ViewUpdater.updateTableView(redaktorTableView, redaktorDAO);
    }

    private void updateSekcjaChoiceBox() {
        ViewUpdater.updateChoiceBox(sekcjaChoiceBox, sekcjaDAO);
    }
}
