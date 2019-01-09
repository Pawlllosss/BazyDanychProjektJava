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
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.controller.helper.observable.ObservableViewListWrapper;
import redaktor.controller.helper.observable.update.ObservableListUpdater;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.initialize.display.SekcjaChoiceBoxDisplayNameRetriever;
import redaktor.model.program.Program;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.model.program.view.ProgramRedaktorCount;
import redaktor.view.AlertBox;

import java.util.List;

public class ProgramTabController implements EntityController<Program> {

    private ProgramDAO programDAO;
    private SekcjaDAO sekcjaDAO;

    private ObservableViewListWrapper<ProgramRedaktorCount> programRedaktorCountObservableViewListWrapper;

    @FXML
    private TableView<Program> programTableView;
    @FXML
    private TableView<ProgramRedaktorCount> programRedaktorCountTableView;
    @FXML
    private ChoiceBox<Redaktor> redaktorChoiceBox;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TextField opisTextField;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;

    private static ObservableEntityListWrapper<Program> programObservableEntityListWrapper;

    @FXML
    private void initialize() {

        programDAO = ProgramDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        programObservableEntityListWrapper = new ObservableEntityListWrapper<>(programDAO);
        programRedaktorCountObservableViewListWrapper = new ObservableViewListWrapper<>(new ObservableListUpdater<ProgramRedaktorCount>() {
            @Override
            public void executeUpdate(ObservableList<ProgramRedaktorCount> observableList) {
                List<ProgramRedaktorCount> programRedaktorCountList = programDAO.getProgramRedaktorCount();
                observableList.setAll(programRedaktorCountList);
            }
        });

        initializeProgramTableView();
        //TODO: in Java8 I could use lamba and functional features...
        ViewInitializer.initializeChoiceBox(redaktorChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, new SekcjaChoiceBoxDisplayNameRetriever());

        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Sekcja> sekcjaObservableList = SekcjeTabController.getObservableList();
        sekcjaChoiceBox.setItems(sekcjaObservableList);

        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        redaktorChoiceBox.setItems(redaktorObservableList);
    }

    //TODO: static in interface?
    public static ObservableList<Program> getObservableList() {
        return programObservableEntityListWrapper.getObservableList();
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
        ViewInitializer.setObservableListToTableView(programTableView, programObservableEntityListWrapper.getObservableList());
    }

    @FXML
    private void addProgram() {
        Sekcja sekcja = sekcjaChoiceBox.getValue();
        String nazwa = nazwaTextField.getText();
        String opis =  opisTextField.getText();
        long sekcjaId = sekcja.getSekcjaId();

        Program program = new Program(0, nazwa, opis, sekcjaId);
        programDAO.save(program);
        programObservableEntityListWrapper.updateObservableList();
    }

    @FXML
    //TODO: view with assigned redaktors
    private void assignRedaktorToProgram() {

        //TODO: check if redaktor is already assigned

        Redaktor assignedRedaktor = redaktorChoiceBox.getValue();
        Program chosenProgram = TableViewHelper.getSelectedItem(programTableView);

        if(assignedRedaktor != null && chosenProgram != null) {
            long redaktorId = assignedRedaktor.getRedaktorId();
            long programId = chosenProgram.getProgramId();
            programDAO.saveRedaktorProgramRelation(redaktorId, programId);
            programRedaktorCountObservableViewListWrapper.updateObservableList();
        } else {
            AlertBox alertBox = new AlertBox("Nie wybrano redaktora lub programu!");
            alertBox.show();
        }
    }
}
