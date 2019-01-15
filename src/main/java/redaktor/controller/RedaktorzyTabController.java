package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.redaktor.RedaktorForm;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;


public class RedaktorzyTabController implements EntityController<Redaktor> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private RedaktorForm redaktorForm;

    @FXML
    private TableView<Redaktor> redaktorTableView;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    private static ObservableEntityListWrapper<Redaktor> redaktorObservableEntityListWrapper;

    @FXML
    private void initialize() {

        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        redaktorObservableEntityListWrapper = new ObservableEntityListWrapper<>(redaktorDAO);

        initializeRedaktorTableView();
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, sekcja -> sekcja.getNazwa());

        redaktorForm = new RedaktorForm(this);
        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Sekcja> sekcjaObservableList = SekcjeTabController.getObservableList();
        sekcjaChoiceBox.setItems(sekcjaObservableList);
    }

    //TODO: maybe create abstract class?
//    @Override
    public static ObservableList<Redaktor> getObservableList() {
        return redaktorObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addRedactor() {
        if(redaktorForm.isFormCorrectlyFilled()) {
            Redaktor redaktor = redaktorForm.readForm();
            redaktorDAO.save(redaktor);
            redaktorObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void deleteRedaktor() {
        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            long redaktorId = redaktor.getRedaktorId();
            redaktorDAO.delete(redaktorId);
            redaktorObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void editRedaktor() {

        Redaktor redaktorToEdit = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktorToEdit != null) {

            if(!redaktorForm.isFormCorrectlyFilled()) {
                WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
                warningAlert.showAndWait();
                return;
            }

            if(!redaktorForm.isFormDifferentFromEntity(redaktorToEdit)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
                long editedRedaktorId = redaktorToEdit.getRedaktorId();
                Redaktor editedRedaktor = redaktorForm.readForm();
                editedRedaktor.setRedaktorId(editedRedaktorId);

                redaktorDAO.update(redaktorToEdit, editedRedaktor);
                redaktorObservableEntityListWrapper.updateObservableList();
            }

        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void loadRedaktorEditForm() {
        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            redaktorForm.loadValuesIntoForm(redaktor);
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora!");
            warningAlert.showAndWait();
        }
    }

    public String getImieFromTextField() {
        return imieTextField.getText();
    }

    public void setImieToTextField(String imie) {
        imieTextField.setText(imie);
    }

    public String getNazwiskoFromTextField() {
        return nazwiskoTextField.getText();
    }

    public void setNazwiskoToTextField(String nazwisko) {
        imieTextField.setText(nazwisko);
    }

    public Optional<Sekcja> getSekcjaFromChoiceBox() {
        return Optional.ofNullable(sekcjaChoiceBox.getValue());
    }

    public void setSekcjaToChoiceBox(Sekcja sekcja) {
        sekcjaChoiceBox.setValue(sekcja);
    }

    private void initializeRedaktorTableView() {
        TableColumn<Redaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = ViewInitializer.createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = ViewInitializer.createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        sekcjaNazwaColumn.setCellValueFactory(redaktorStringCellDataFeatures -> {
            Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
            Optional<Sekcja> sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
            String sekcjaNazwa = sekcja.map(sekcjaLambda -> sekcjaLambda.getNazwa()).orElse(null);

            return new SimpleStringProperty(sekcjaNazwa);
        });

        ViewInitializer.addColumnsToTableView(redaktorTableView, redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(redaktorTableView, redaktorObservableEntityListWrapper.getObservableList());
    }
}