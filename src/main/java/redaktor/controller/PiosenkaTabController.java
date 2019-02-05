package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.PiosenkaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormChecker;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.piosenka.PiosenkaForm;
import redaktor.controller.helper.crud.EntityAdder;
import redaktor.controller.helper.crud.EntityDeleter;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.table.PiosenkaTableViewWrapper;
import redaktor.model.Piosenka;

public class PiosenkaTabController implements EntityController<Piosenka> {
    private PiosenkaDAO piosenkaDAO;
    private PiosenkaForm piosenkaForm;
    private PiosenkaTableViewWrapper piosenkaTableViewWrapper;

    @FXML
    private TableView<Piosenka> piosenkaTableView;
    @FXML
    private TextField tytulTextField;
    @FXML
    private TextField wykonawcaTextField;

    private static ObservableEntityListWrapper<Piosenka> piosenkaObservableEntityListWrapper;

    @FXML
    private void initialize() {
        piosenkaDAO = PiosenkaDAO.getInstance();
        piosenkaObservableEntityListWrapper = new ObservableEntityListWrapper<>(piosenkaDAO);

        piosenkaTableViewWrapper = new PiosenkaTableViewWrapper(piosenkaTableView);
        piosenkaTableViewWrapper.initialize(piosenkaObservableEntityListWrapper);

        piosenkaForm = new PiosenkaForm(this);
        MainController.addEntityController(this);
    }

    @Override
    public void setItemsFromOtherControllers() {
    }

    public static ObservableList<Piosenka> getObservableList() {
        return piosenkaObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addPiosenka() {
        EntityAdder.tryToAddEntity(piosenkaForm, piosenkaDAO, piosenkaObservableEntityListWrapper);
    }

    @FXML
    private void deletePiosenka() {
        EntityDeleter.tryToDeleteEntity(piosenkaTableViewWrapper, piosenkaDAO, piosenkaObservableEntityListWrapper, piosenka -> piosenka.getPiosenkaId());
    }

    @FXML
    private void editPiosenka() {
        Piosenka piosenkaToEdit = piosenkaTableViewWrapper.getSelectedItem();

        if(piosenkaToEdit != null) {
            if(FormChecker.checkIfFormSuitableForEditAndDisplayWarningIfNot(piosenkaForm, piosenkaToEdit))
            {
                long editedPiosenkaId = piosenkaToEdit.getPiosenkaId();
                Piosenka editedPiosenka = piosenkaForm.readForm();
                editedPiosenka.setPiosenkaId(editedPiosenkaId);

                piosenkaDAO.update(piosenkaToEdit, editedPiosenka);
                piosenkaObservableEntityListWrapper.updateObservableList();
            }
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano piosenki!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadPiosenkaEditForm() {
        FormLoader.tryToLoadValuesIntoForm(piosenkaTableView, piosenkaForm, "Nie wybrano Piosenka!");
    }

    public String getTytulFromTextField() {
        return tytulTextField.getText();
    }

    public void setTytulToTextField(String tytul) {
        tytulTextField.setText(tytul);
    }

    public String getWykonawcaFromTextField() {
        return wykonawcaTextField.getText();
    }

    public void setWykonawcaToTextField(String wykonawca) {
        wykonawcaTextField.setText(wykonawca);
    }

}