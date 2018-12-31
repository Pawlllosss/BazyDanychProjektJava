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
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.helper.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.initialize.display.SekcjaChoiceBoxDisplayNameRetriever;
import redaktor.model.Program;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

public class ProgramTabController implements ValueObjectController<Program> {

    private ProgramDAO programDAO;
    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private static ObservableListWrapper<Program> programObservableListWrapper;

    @FXML
    private TableView<Program> programTableView;
    @FXML
    private ChoiceBox<Redaktor> redaktorChoiceBox;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TextField opisTextField;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;

    @FXML
    private void initialize() {

        programDAO = ProgramDAO.getInstance();
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        programObservableListWrapper= new ObservableListWrapper<>(programDAO);

        initializeProgramTableView();
        //TODO: in Java8 I could use lamba and functional features...
        ViewInitializer.initializeChoiceBox(redaktorChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());

        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, new SekcjaChoiceBoxDisplayNameRetriever());

        MainController.addValueObjectController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Sekcja> sekcjaObservableList = SekcjeTabController.getObservableList();
        sekcjaChoiceBox.setItems(sekcjaObservableList);

        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        redaktorChoiceBox.setItems(redaktorObservableList);
    }

    public static ObservableList<Program> getObservableList() {
        return programObservableListWrapper.getObservableList();
    }


    private void initializeProgramTableView() {
        TableColumn<Program, Long> programIdColumn = ViewInitializer.createColumn("Id programu", "programId", 120);
        TableColumn<Program, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Program, String> opisColumn = ViewInitializer.createColumn("Opis", "opis", 50);
        TableColumn<Program, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        //TODO: java7 doesn't have lambdas...
        sekcjaNazwaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Program, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Program, String> programStringCellDataFeatures) {
                Program program = programStringCellDataFeatures.getValue();
                Sekcja sekcja = sekcjaDAO.get(program.getSekcjaId());
                String sekcjaNazwa = sekcja.getNazwa();

                return new SimpleStringProperty(sekcjaNazwa);
            }
        });

        ViewInitializer.addColumnsToTableView(programTableView, programIdColumn, nazwaColumn, opisColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(programTableView, programObservableListWrapper.getObservableList());
    }

    @FXML
    private void addProgram() {
        Sekcja sekcja = sekcjaChoiceBox.getValue();
        String nazwa = nazwaTextField.getText();
        String opis =  opisTextField.getText();
        long sekcjaId = sekcja.getSekcjaId();

        Program program = new Program(0, nazwa, opis, sekcjaId);
        programDAO.save(program);
        programObservableListWrapper.updateObservableList();
    }

    @FXML
    private void assignRedaktorToProgram() {

    }
}
