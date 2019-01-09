package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;


public class RedaktorzyTabController implements EntityController<Redaktor> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private static ObservableEntityListWrapper<Redaktor> redaktorObservableEntityListWrapper;

    @FXML
    private TableView<Redaktor> redaktorTableView;
    @FXML
    private ChoiceBox<Sekcja> sekcjaChoiceBox;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private void initialize() {

        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        redaktorObservableEntityListWrapper = new ObservableEntityListWrapper<>(redaktorDAO);

        initializeRedaktorTableView();
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, sekcja -> sekcja.getNazwa());

        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Sekcja> sekcjaObservableList = SekcjeTabController.getObservableList();
        sekcjaChoiceBox.setItems(sekcjaObservableList);
    }

    //TODO: wait for Java8...
//    @Override
    public static ObservableList<Redaktor> getObservableList() {
        return redaktorObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addRedactor() {
        RedaktorFormValues redaktorFormValues = readRedaktorForm();

        //TODO: add some field validation and maybe triggers? BLOCK WITHOUT SECTION
        Sekcja sekcja = redaktorFormValues.sekcja;
        String imie = redaktorFormValues.imie;
        String nazwisko =  redaktorFormValues.nazwisko;
        long sekcjaId = sekcja.getSekcjaId();

        Redaktor redaktor = new Redaktor(0, imie, nazwisko, sekcjaId);
        redaktorDAO.save(redaktor);
        redaktorObservableEntityListWrapper.updateObservableList();
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

        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            long redaktorId = redaktor.getRedaktorId();

            RedaktorFormValues redaktorFormValues = readRedaktorForm();

            if(!checkIfAnyFieldWasEdited(redaktor, redaktorFormValues)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
                String imie = redaktorFormValues.imie;
                String nazwisko = redaktorFormValues.nazwisko;
                long sekcjaId = redaktorFormValues.sekcja.getSekcjaId();
                Redaktor editedRedaktor = new Redaktor(redaktorId, imie, nazwisko, sekcjaId);

                redaktorDAO.update(redaktor, editedRedaktor);
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
            Long sekcjaId = redaktor.getSekcjaId();
            Sekcja sekcja = sekcjaDAO.get(sekcjaId);

            imieTextField.setText(redaktor.getImie());
            nazwiskoTextField.setText(redaktor.getNazwisko());
            sekcjaChoiceBox.setValue(sekcja);
            redaktorObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano redaktora!");
            warningAlert.showAndWait();
        }
    }

    private void initializeRedaktorTableView() {
        TableColumn<Redaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = ViewInitializer.createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = ViewInitializer.createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        sekcjaNazwaColumn.setCellValueFactory(redaktorStringCellDataFeatures -> {
            Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
            Sekcja sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
            String sekcjaNazwa = sekcja.getNazwa();

            return new SimpleStringProperty(sekcjaNazwa);
        });

        ViewInitializer.addColumnsToTableView(redaktorTableView, redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(redaktorTableView, redaktorObservableEntityListWrapper.getObservableList());
    }

    private boolean checkIfAnyFieldWasEdited(Redaktor redaktor, RedaktorFormValues redaktorFormValues) {
        boolean wasEdited = false;

        if(!redaktor.getImie().equals(redaktorFormValues.imie)) {
            wasEdited = true;
        }
        else if (!redaktor.getNazwisko().equals(redaktorFormValues.nazwisko)) {
            wasEdited = true;
        }
        else if (redaktor.getSekcjaId() != redaktorFormValues.sekcja.getSekcjaId()) {
            wasEdited = true;
        }

        return wasEdited;
    }

    private RedaktorFormValues readRedaktorForm() {

        RedaktorFormValues redaktorFormValues = new RedaktorFormValues();

        redaktorFormValues.imie = imieTextField.getText();
        redaktorFormValues.nazwisko = nazwiskoTextField.getText();
        redaktorFormValues.sekcja = sekcjaChoiceBox.getValue();

        return redaktorFormValues;
    }

    class RedaktorFormValues {
        private String imie;
        private String nazwisko;
        private Sekcja sekcja;
    }

}