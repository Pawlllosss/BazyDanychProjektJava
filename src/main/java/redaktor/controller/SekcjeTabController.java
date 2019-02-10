package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormChecker;
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.sekcja.SekcjaForm;
import redaktor.controller.crud.EntityAdder;
import redaktor.controller.crud.EntityDeleter;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.table.SekcjaTableViewWrapper;
import redaktor.controller.table.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;


public class SekcjeTabController implements EntityController<Sekcja> {
    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private SekcjaForm sekcjaForm;
    private SekcjaTableViewWrapper sekcjaTableViewWrapper;

    @FXML
    private TableView<Sekcja> sekcjaTableView;
    @FXML
    private ChoiceBox<Redaktor> szefChoiceBox;
    @FXML
    private TextField nazwaTextField;

    private static ObservableEntityNoUpdateArgumentsListWrapper<Sekcja> sekcjaObservableEntityListWrapper;

    @FXML
    private void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        sekcjaObservableEntityListWrapper = new ObservableEntityNoUpdateArgumentsListWrapper<>(sekcjaDAO);

        sekcjaTableViewWrapper = new SekcjaTableViewWrapper(sekcjaTableView);
        sekcjaTableViewWrapper.initialize(sekcjaObservableEntityListWrapper);

        sekcjaForm = new SekcjaForm(this);
        ViewInitializer.initializeChoiceBox(szefChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());

        MainController.addEntityController(this);
    }

    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        szefChoiceBox.setItems(redaktorObservableList);
    }

    public static ObservableList<Sekcja> getObservableList() {
        return sekcjaObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addSekcja() {
        EntityAdder.tryToAddEntity(sekcjaForm, sekcjaDAO, sekcjaObservableEntityListWrapper);
    }

    @FXML
    private void deleteSekcja() {
        EntityDeleter.tryToDeleteEntity(sekcjaTableViewWrapper, sekcjaDAO, sekcjaObservableEntityListWrapper, Sekcja::getSekcjaId);
    }

    @FXML
    private void editSekcja() {
        Sekcja sekcjaToEdit = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcjaToEdit != null) {
            if(FormChecker.checkIfFormSuitableForEditAndDisplayWarningIfNot(sekcjaForm, sekcjaToEdit)) {
                Long editedSekcjaId = sekcjaToEdit.getSekcjaId();
                Sekcja editedSekcja = sekcjaForm.readForm();
                editedSekcja.setSekcjaId(editedSekcjaId);

                sekcjaDAO.update(sekcjaToEdit, editedSekcja);
                sekcjaObservableEntityListWrapper.updateObservableList();
            }
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano sekcji!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadSekcjaEditForm() {
        FormLoader.tryToLoadValuesIntoForm(sekcjaTableView, sekcjaForm, "Nie wybrano sekcji!");
    }

    public String getNazwaFromTextField() {
        return nazwaTextField.getText();
    }

    public void setNazwaToTextField(String nazwa) {
        nazwaTextField.setText(nazwa);
    }

    public Optional<Redaktor> getSzefFromChoiceBox() {
        return Optional.ofNullable(szefChoiceBox.getValue());
    }

    public void setSzefToChoiceBox(Redaktor szef) {
        szefChoiceBox.setValue(szef);
    }

}
