package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.StudioDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.studio.StudioForm;
import redaktor.controller.helper.crud.EntityAdder;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.table.StudioTableViewWrapper;
import redaktor.model.Studio;

public class StudioTabController implements EntityController<Studio> {

    private StudioDAO studioDAO;
    private StudioForm studioForm;
    private StudioTableViewWrapper studioTableViewWrapper;

    @FXML
    private TableView<Studio> studioTableView;
    @FXML
    private TextField nazwaTextField;
    @FXML
    private TextField nrPokojuTextField;

    private static ObservableEntityNoUpdateArgumentsListWrapper<Studio> studioObservableEntityListWrapper;

    @FXML
    private void initialize() {
        studioDAO = studioDAO.getInstance();
        studioObservableEntityListWrapper = new ObservableEntityNoUpdateArgumentsListWrapper<>(studioDAO);

        studioTableViewWrapper = new StudioTableViewWrapper(studioTableView);
        studioTableViewWrapper.initialize(studioObservableEntityListWrapper);

        addInputCorrectionCheckForNrPokojuTextField();

        studioForm = new StudioForm(this);
        MainController.addEntityController(this);
    }

    @Override
    public void setItemsFromOtherControllers() {
    }

    //TODO: maybe create abstract class?
//    @Override
    public static ObservableList<Studio> getObservableList() {
        return studioObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addStudio() {
        EntityAdder.tryToAddEntity(studioForm, studioDAO, studioObservableEntityListWrapper);
    }

    @FXML
    private void deleteStudio() {
        Studio studio = studioTableViewWrapper.getSelectedItem();

        if(studio != null) {
            long studioId = studio.getStudioId();
            studioDAO.delete(studioId);
            studioObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano Studia!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void editStudio() {
        Studio studioToEdit = studioTableViewWrapper.getSelectedItem();

        if(studioToEdit != null) {
            if(!studioForm.isFormCorrectlyFilled()) {
                WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
                warningAlert.showAndWait();
                return;
            }

            if(!studioForm.isFormDifferentFromEntity(studioToEdit)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
                long editedStudioId = studioToEdit.getStudioId();
                Studio editedStudio = studioForm.readForm();
                editedStudio.setStudioId(editedStudioId);

                studioDAO.update(studioToEdit, editedStudio);
                studioObservableEntityListWrapper.updateObservableList();
            }

        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano studia!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadStudioEditForm() {
        FormLoader.tryToLoadValuesIntoForm(studioTableView, studioForm, "Nie wybrano studia!");
    }

    private void addInputCorrectionCheckForNrPokojuTextField() {
        nrPokojuTextField.textProperty().addListener((observableValue, oldString, newString) -> {
            if (!newString.matches("\\d*")) {
                nrPokojuTextField.setText(oldString);
            }
        });
    }


    public String getNazwaFromTextField() {
        return nazwaTextField.getText();
    }

    public void setNazwaToTextField(String nazwa) {
        nazwaTextField.setText(nazwa);
    }

    public String getNrPokojuFromTextField() {
        return nrPokojuTextField.getText();
    }

    public void setNrPokojuTextField(String nrPokoju) {
        nrPokojuTextField.setText(nrPokoju);
    }

}