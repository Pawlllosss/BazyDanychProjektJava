package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.AudycjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormChecker;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.audycja.AudycjaForm;
import redaktor.controller.helper.crud.EntityAdder;
import redaktor.controller.observable.ObservableCustomUpdateNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableNoUpdateArgumentsListWrapper;
import redaktor.controller.table.AudycjaTableViewWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Audycja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class AudycjaTabController implements EntityController<Audycja> {

    private AudycjaDAO audycjaDAO;

    private AudycjaForm audycjaForm;
    private AudycjaTableViewWrapper audycjaTableViewWrapper;
    private AudycjaTableViewWrapper audycjaDayTableViewWrapper;

    private ObservableNoUpdateArgumentsListWrapper<Audycja> audycjaDayObservableNoUpdateArgumentsListWrapper;

    @FXML
    private TableView<Audycja> audycjaTableView;
    @FXML
    private TableView<Audycja> audycjaDayTableView;
    @FXML
    private ChoiceBox<Program> programChoiceBox;
    @FXML
    private ChoiceBox<Studio> studioChoiceBox;
    @FXML
    private DatePicker dataPoczatekDzienDatePicker;
    @FXML
    private TextField dataPoczatekGodzinaTextField;
    @FXML
    private DatePicker dataKoniecDzienDatePicker;
    @FXML
    private TextField dataKoniecGodzinaTextField;
    @FXML
    private DatePicker audycjaDayDatePicker;

    private static ObservableEntityNoUpdateArgumentsListWrapper<Audycja> audycjaObservableEntityListWrapper;

    @FXML
    private void initialize() {
        audycjaDAO = AudycjaDAO.getInstance();
        audycjaObservableEntityListWrapper = new ObservableEntityNoUpdateArgumentsListWrapper<>(audycjaDAO);

        audycjaTableViewWrapper = new AudycjaTableViewWrapper(audycjaTableView);
        audycjaTableViewWrapper.initialize(audycjaObservableEntityListWrapper);

        audycjaDayObservableNoUpdateArgumentsListWrapper = new ObservableCustomUpdateNoUpdateArgumentsListWrapper<>(observableList -> {
            LocalDate localDate = getAudycjaDayFromDatePicker();
            if(localDate != null) {
                Date audycjaDate = Date.valueOf(localDate);
                observableList.setAll(audycjaDAO.getAudycjasInDay(audycjaDate));
            }
        });

        audycjaDayTableViewWrapper = new AudycjaTableViewWrapper(audycjaDayTableView);
        audycjaDayTableViewWrapper.initialize(audycjaDayObservableNoUpdateArgumentsListWrapper);

        ViewInitializer.initializeChoiceBox(programChoiceBox, Program::getNazwa);
        ViewInitializer.initializeChoiceBox(studioChoiceBox, Studio::getNazwa);

        addTimeCorrectionCheckForTextField(dataPoczatekGodzinaTextField);
        addTimeCorrectionCheckForTextField(dataKoniecGodzinaTextField);

        audycjaForm = new AudycjaForm(this);
        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Program> programObservableList = ProgramTabController.getObservableList();
        programChoiceBox.setItems(programObservableList);

        ObservableList<Studio> studioObservableList = StudioTabController.getObservableList();
        studioChoiceBox.setItems(studioObservableList);
    }

    public static ObservableList<Audycja> getObservableList() {
        return audycjaObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addAudycja() {
        EntityAdder.tryToAddEntity(audycjaForm, audycjaDAO, audycjaObservableEntityListWrapper);
    }

    @FXML
    private void deleteAudycja() {
        Audycja audycja = audycjaTableViewWrapper.getSelectedItem();

        if(audycja != null) {
            long audycjaId = audycja.getAudycjaId();
            audycjaDAO.delete(audycjaId);
            audycjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano audycji!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void editAudycja() {
        Audycja audycjaToEdit = audycjaTableViewWrapper.getSelectedItem();

        if(audycjaToEdit != null) {
            if(FormChecker.checkIfFormSuitableForEditAndDisplayWarningIfNot(audycjaForm, audycjaToEdit)) {
                long editedAudycjaId = audycjaToEdit.getAudycjaId();
                Audycja editedAudycja = audycjaForm.readForm();
                editedAudycja.setAudycjaId(editedAudycjaId);

                audycjaDAO.update(audycjaToEdit, editedAudycja);
                audycjaObservableEntityListWrapper.updateObservableList();
            }

        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano audycji!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadAudycjaEditForm() {
        FormLoader.tryToLoadValuesIntoForm(audycjaTableView, audycjaForm, "Nie wybrano audycji!");
    }

    @FXML
    private void fillForChosenDayAudycjaDayTableView() {
        audycjaDayObservableNoUpdateArgumentsListWrapper.updateObservableList();
    }

    private void addTimeCorrectionCheckForTextField(TextField textField) {
        textField.textProperty().addListener((observableValue, oldString, newString) -> {
            if (!newString.matches("(\\d{0,2}:?){0,2}")) {
                textField.setText(oldString);
            }
        });
    }

    public LocalDate getDataPoczatekDzienFromDatePicker() {
        return dataPoczatekDzienDatePicker.getValue();
    }

    public void setDataPoczatekDzienToDatePicker(LocalDate dataPoczatekDzienDate) {
        dataPoczatekDzienDatePicker.setValue(dataPoczatekDzienDate);
    }

    public String getDataPoczatekGodzinaFromTextField() {
        return dataPoczatekGodzinaTextField.getText();
    }

    public void setDataPoczatekGodzinaToTextField(String dataPoczatekGodzina) {
        dataPoczatekGodzinaTextField.setText(dataPoczatekGodzina);
    }

    public LocalDate getDataKoniecDzienFromDatePicker() {
        return dataKoniecDzienDatePicker.getValue();
    }

    public void setDataKoniecDzienToDatePicker(LocalDate dataKoniecDzienDate) {
        dataKoniecDzienDatePicker.setValue(dataKoniecDzienDate);
    }

    public String getDataKoniecGodzinaFromTextField() {
        return dataKoniecGodzinaTextField.getText();
    }

    public void setDataKoniecGodzinaToTextField(String dataKoniecGodzina) {
        dataKoniecGodzinaTextField.setText(dataKoniecGodzina);
    }
    public Optional<Program> getProgramFromChoiceBox() {
        return Optional.ofNullable(programChoiceBox.getValue());
    }

    public void setProgramToChoiceBox(Program program) {
        programChoiceBox.setValue(program);
    }

    public Optional<Studio> getStudioFromChoiceBox() {
        return Optional.ofNullable(studioChoiceBox.getValue());
    }

    public void setStudioToChoiceBox(Studio studio) {
        studioChoiceBox.setValue(studio);
    }

    public LocalDate getAudycjaDayFromDatePicker() {
       return  audycjaDayDatePicker.getValue();
    }
}
