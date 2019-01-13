package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.program.ProgramForm;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.controller.helper.observable.ObservableViewListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.model.program.Program;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.model.program.view.ProgramRedaktorCount;

import java.util.Optional;

public class ProgramTabController implements EntityController<Program> {

    private ProgramDAO programDAO;
    private SekcjaDAO sekcjaDAO;

    private ProgramForm programForm;

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

    private ObservableViewListWrapper<ProgramRedaktorCount> programRedaktorCountObservableViewListWrapper;

    private static ObservableEntityListWrapper<Program> programObservableEntityListWrapper;

    @FXML
    private void initialize() {
        programDAO = ProgramDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        programObservableEntityListWrapper = new ObservableEntityListWrapper<>(programDAO);
        programRedaktorCountObservableViewListWrapper = new ObservableViewListWrapper<>((observableList) -> observableList.setAll(programDAO.getProgramRedaktorCount()));

        initializeProgramTableView();
        ViewInitializer.initializeChoiceBox(redaktorChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, s -> s.getNazwa());

        programForm = new ProgramForm(this);
        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Sekcja> sekcjaObservableList = SekcjeTabController.getObservableList();
        sekcjaChoiceBox.setItems(sekcjaObservableList);

        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        redaktorChoiceBox.setItems(redaktorObservableList);
    }

    //TODO: move to abstract
    public static ObservableList<Program> getObservableList() {
        return programObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addProgram() {
        if(programForm.isFormCorrectlyFilled()) {
            Program program = programForm.readForm();
            programDAO.save(program);
            programObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    //TODO: view with assigned redaktors
    private void assignRedaktorToProgram() {
        //TODO: check if redaktor is already assigned
        Redaktor assignedRedaktor = redaktorChoiceBox.getValue();
        Program chosenProgram = TableViewHelper.getSelectedItem(programTableView);

        if(assignedRedaktor != null && chosenProgram != null) {
            programDAO.saveRedaktorProgramRelation(assignedRedaktor.getRedaktorId(), chosenProgram.getProgramId());
            programRedaktorCountObservableViewListWrapper.updateObservableList();
        } else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora lub programu!");
            warningAlert.showAndWait();
        }
    }

    public String getNazwaFromTextField() {
        return nazwaTextField.getText();
    }

    public void setNawaToTextField(String nazwa) {
        nazwaTextField.setText(nazwa);
    }

    public String getOpisFromTextField() {
        return opisTextField.getText();
    }

    public void setOpisToTextField(String opis) {
        opisTextField.setText(opis);
    }

    public Optional<Sekcja> getSekcjaFromChoiceBox() {
        return Optional.ofNullable(sekcjaChoiceBox.getValue());
    }

    public void setSekcjaToChoiceBox(Sekcja sekcja) {
        sekcjaChoiceBox.setValue(sekcja);
    }

    private void initializeProgramTableView() {
        TableColumn<Program, Long> programIdColumn = ViewInitializer.createColumn("Id programu", "programId", 120);
        TableColumn<Program, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Program, String> opisColumn = ViewInitializer.createColumn("Opis", "opis", 50);
        TableColumn<Program, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        sekcjaNazwaColumn.setCellValueFactory(programStringCellDataFeatures -> {
            Program program = programStringCellDataFeatures.getValue();
            Optional<Sekcja> sekcja = sekcjaDAO.get(program.getSekcjaId());
            String sekcjaNazwa = sekcja.map(sekcjaLambda -> sekcjaLambda.getNazwa()).orElse("");

            return new SimpleStringProperty(sekcjaNazwa);
        });

        ViewInitializer.addColumnsToTableView(programTableView, programIdColumn, nazwaColumn, opisColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(programTableView, programObservableEntityListWrapper.getObservableList());
    }
}
