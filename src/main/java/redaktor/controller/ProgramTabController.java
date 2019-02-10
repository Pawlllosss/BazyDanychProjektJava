package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormChecker;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.program.ProgramForm;
import redaktor.controller.helper.crud.EntityAdder;
import redaktor.controller.observable.ObservableCustomUpdateNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.listener.ObservableListWrapperUpdateListener;
import redaktor.controller.table.ProgramPrzypisanyRedaktorTableViewWrapper;
import redaktor.controller.table.ProgramRedaktorCountTableViewWrapper;
import redaktor.controller.table.ProgramTableViewWrapper;
import redaktor.controller.table.TableViewHelper;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.model.program.Program;
import redaktor.model.program.view.ProgramPrzypisanyRedaktor;
import redaktor.model.program.view.ProgramRedaktorCount;

import java.util.Optional;

public class ProgramTabController implements EntityController<Program> {
    private ProgramDAO programDAO;
    private SekcjaDAO sekcjaDAO;

    private ProgramForm programForm;
    private ProgramTableViewWrapper programTableViewWrapper;
    private ProgramRedaktorCountTableViewWrapper programRedaktorCountTableViewWrapper;
    private ProgramPrzypisanyRedaktorTableViewWrapper programPrzypisanyRedaktorTableViewWrapper;

    @FXML
    private TableView<Program> programTableView;
    @FXML
    private TableView<ProgramRedaktorCount> programRedaktorCountTableView;
    @FXML
    private TableView<ProgramPrzypisanyRedaktor> programPrzypisanyRedaktorTableView;
    @FXML
    private ChoiceBox<Redaktor> redaktorChoiceBox;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TextField opisTextField;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;

    private ObservableCustomUpdateNoUpdateArgumentsListWrapper<ProgramRedaktorCount> programRedaktorCountObservableCustomUpdateListWrapper;
    private ObservableCustomUpdateNoUpdateArgumentsListWrapper<ProgramPrzypisanyRedaktor> programPrzypisanyRedaktorObservableCustomUpdateListWrapper;

    private ObservableListWrapperUpdateListener observableListWrapperUpdateListener;

    private static ObservableEntityNoUpdateArgumentsListWrapper<Program> programObservableEntityListWrapper;

    @FXML
    private void initialize() {
        programDAO = ProgramDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        programObservableEntityListWrapper = new ObservableEntityNoUpdateArgumentsListWrapper<>(programDAO);

        programRedaktorCountObservableCustomUpdateListWrapper = new ObservableCustomUpdateNoUpdateArgumentsListWrapper<>((observableList) -> observableList.setAll(programDAO.getProgramRedaktorCount()));
        programPrzypisanyRedaktorObservableCustomUpdateListWrapper = new ObservableCustomUpdateNoUpdateArgumentsListWrapper<>((observableList) -> observableList.setAll(programDAO.getProgramPrzypisanyRedaktor()));

        observableListWrapperUpdateListener = new ObservableListWrapperUpdateListener();
        observableListWrapperUpdateListener.appendObservableViewListWrappers(programRedaktorCountObservableCustomUpdateListWrapper, programPrzypisanyRedaktorObservableCustomUpdateListWrapper);

        programTableViewWrapper = new ProgramTableViewWrapper(programTableView);
        programTableViewWrapper.initialize(programObservableEntityListWrapper);

        programRedaktorCountTableViewWrapper = new ProgramRedaktorCountTableViewWrapper(programRedaktorCountTableView);
        programRedaktorCountTableViewWrapper.initialize(programRedaktorCountObservableCustomUpdateListWrapper);

        programPrzypisanyRedaktorTableViewWrapper = new ProgramPrzypisanyRedaktorTableViewWrapper(programPrzypisanyRedaktorTableView);
        programPrzypisanyRedaktorTableViewWrapper.initialize(programPrzypisanyRedaktorObservableCustomUpdateListWrapper);

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

    public static ObservableList<Program> getObservableList() {
        return programObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addProgram() {
        EntityAdder.tryToAddEntity(programForm, programDAO, programObservableEntityListWrapper);
    }

    @FXML
    private void deleteProgram() {
        Program program = TableViewHelper.getSelectedItem(programTableView);

        if(program != null) {
            long programId = program.getProgramId();
            programDAO.delete(programId);
            programObservableEntityListWrapper.updateObservableList();
            observableListWrapperUpdateListener.updateLists();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano programu!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void editProgram() {
        Program programToEdit = TableViewHelper.getSelectedItem(programTableView);

        if(programToEdit != null) {
            if(FormChecker.checkIfFormSuitableForEditAndDisplayWarningIfNot(programForm, programToEdit)){
                long editedProgramId = programToEdit.getProgramId();
                Program editedProgram = programForm.readForm();
                editedProgram.setProgramId(editedProgramId);

                programDAO.update(programToEdit, editedProgram);
                programObservableEntityListWrapper.updateObservableList();
                observableListWrapperUpdateListener.updateLists();
            }

        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano programu!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadProgramEditForm() {
        FormLoader.tryToLoadValuesIntoForm(programTableView, programForm, "Nie wybrano programu!");
    }

    @FXML
    private void assignRedaktorToProgram() {
        Redaktor assignedRedaktor = redaktorChoiceBox.getValue();
        Program chosenProgram = TableViewHelper.getSelectedItem(programTableView);

        if(assignedRedaktor != null && chosenProgram != null) {
            programDAO.saveRedaktorProgramRelation(assignedRedaktor.getRedaktorId(), chosenProgram.getProgramId());
            observableListWrapperUpdateListener.updateLists();
        } else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora lub programu!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void unassignRedaktorFromProgram() {
        ProgramPrzypisanyRedaktor programPrzypisanyRedaktor = TableViewHelper.getSelectedItem(programPrzypisanyRedaktorTableView);

        if(programPrzypisanyRedaktor != null) {
            programDAO.deleteRedaktorProgramRelation(programPrzypisanyRedaktor.getProgramId(), programPrzypisanyRedaktor.getRedaktorId());
            observableListWrapperUpdateListener.updateLists();
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

}
