package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import redaktor.DAO.AudycjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.audycja.AudycjaForm;
import redaktor.controller.helper.crud.EntityAdder;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.table.AudycjaTableViewWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Audycja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.time.LocalDate;
import java.util.Optional;

//TODO: hour format validator
public class AudycjaTabController implements EntityController<Audycja> {

    private AudycjaDAO audycjaDAO;

    private AudycjaForm audycjaForm;
    private AudycjaTableViewWrapper audycjaTableViewWrapper;

    @FXML
    private TableView<Audycja> audycjaTableView;
    @FXML
    private ChoiceBox<Program> programChoiceBox;
    @FXML
    private ChoiceBox<Studio> studioChoiceBox;
    @FXML
    private DatePicker dataPoczatekDzienDatePicker;
    @FXML
    private TextField dataPoczatekGodzinaTextField;
    @FXML
    private TextField czasTrwaniaTextField;

    private static ObservableEntityListWrapper<Audycja> audycjaObservableEntityListWrapper;

    @FXML
    private void initialize() {
        audycjaDAO = AudycjaDAO.getInstance();
        audycjaObservableEntityListWrapper = new ObservableEntityListWrapper<>(audycjaDAO);

        audycjaTableViewWrapper = new AudycjaTableViewWrapper(audycjaTableView);
        audycjaTableViewWrapper.initialize(audycjaObservableEntityListWrapper);

        ViewInitializer.initializeChoiceBox(programChoiceBox, p -> p.getNazwa());
        ViewInitializer.initializeChoiceBox(studioChoiceBox, s -> s.getNazwa());

        //TODO: move to separate function
        dataPoczatekGodzinaTextField.textProperty().addListener((observableValue, oldString, newString) -> {
            if (!newString.matches("(\\d{0,2}:?){0,2}")) {
                dataPoczatekGodzinaTextField.setText(oldString);
            }
        });

        //TODO: this as well
        czasTrwaniaTextField.textProperty().addListener((observableValue, oldString, newString) -> {
            if (!newString.matches("(\\d{0,2}:?){0,2}")) {
                czasTrwaniaTextField.setText(oldString);
            }
        });


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

    //TODO: move to abstract
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

            if(!audycjaForm.isFormCorrectlyFilled()) {
                WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
                warningAlert.showAndWait();
                return;
            }

            if(!audycjaForm.isFormDifferentFromEntity(audycjaToEdit)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
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

    public String getCzasTrwaniaFromTextField() {
        return czasTrwaniaTextField.getText();
    }

    public void setCzasTrwaniaToTextField(String czasTrwania) {
        czasTrwaniaTextField.setText(czasTrwania);
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
}
