package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;


public class SekcjeTabController implements EntityController<Sekcja> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private static ObservableEntityListWrapper<Sekcja> sekcjaObservableEntityListWrapper;

    @FXML
    private TableView<Sekcja> sekcjaTableView;
    @FXML
    private ChoiceBox<Redaktor> szefChoiceBox;
    @FXML
    private TextField nazwaTextField;

    @FXML
    private void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        sekcjaObservableEntityListWrapper = new ObservableEntityListWrapper<>(sekcjaDAO);

        initializeSekcjaTableView();
        ViewInitializer.initializeChoiceBox(szefChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());

        MainController.addEntityController(this);
    }

    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        szefChoiceBox.setItems(redaktorObservableList);
    }

    //TODO: wait for Java8... (update: NOPE, maybe use abstract class)
//    @Override
    public static ObservableList<Sekcja> getObservableList() {
        return sekcjaObservableEntityListWrapper.getObservableList();
    }

    @FXML
    private void addSekcja() {
        //TODO: it must also provide option to add without szef
        Redaktor szef = szefChoiceBox.getValue();
        String nazwa =  nazwaTextField.getText();

        //TODO: maybe null special case would be better?
        Long szefId = null;
        if(szef != null) {
            szefId = szef.getRedaktorId();
        }

        Sekcja sekcja = new Sekcja(0L, nazwa, szefId);
        sekcjaDAO.save(sekcja);
        sekcjaObservableEntityListWrapper.updateObservableList();
    }

    @FXML
    private void deleteSekcja() {

        Sekcja sekcja = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcja != null) {
            long sekcjaId = sekcja.getSekcjaId();
            sekcjaDAO.delete(sekcjaId);
            sekcjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano sekcji!");
            warningAlert.showAndWait();
        }

    }

    @FXML
    private void editSekcja() {

        Sekcja sekcja = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcja != null) {
            Long sekcjaId = sekcja.getSekcjaId();

            SekcjaFormValues sekcjaFormValues = readSekcjaForm();

            //TODO: validate
            if(!checkIfAnyFieldWasEdited(sekcja, sekcjaFormValues)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
                String nazwa = sekcjaFormValues.nazwa;
                Long szefId = sekcjaFormValues.szef.getRedaktorId();
                Sekcja editedSekcja = new Sekcja(sekcjaId, nazwa, szefId);

                sekcjaDAO.update(sekcja, editedSekcja);
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
        Sekcja sekcja = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcja != null) {
            long szefId = sekcja.getSekcjaId();
            Optional<Redaktor> szef = redaktorDAO.get(szefId);

            nazwaTextField.setText(sekcja.getNazwa());
            szefChoiceBox.setValue(szef.orElse(null));

            sekcjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano sekcji!");
            warningAlert.showAndWait();
        }
    }

    //TODO: handle redactors
    private void initializeSekcjaTableView() {
        TableColumn<Sekcja, Long> sekcjaIdColumn = ViewInitializer.createColumn("Id sekcja", "sekcjaId", 80);
        TableColumn<Sekcja, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Sekcja, String> szefIdColumn = ViewInitializer.createColumn("Id szef", "szefId", 50);
        TableColumn<Sekcja, String> szefImieColumn = new TableColumn<>("Imie szefa");
        TableColumn<Sekcja, String> szefNazwiskoColumn = new TableColumn<>("Nazwisko szefa");

        szefImieColumn.setCellValueFactory(szefStringCellDataFeatures -> {
            Sekcja sekcja = szefStringCellDataFeatures.getValue();
            Optional<Redaktor> szef = redaktorDAO.get(sekcja.getSzefId());
            String szefImie = szef.map(szefLambda -> szefLambda.getImie()).orElse("");

            return new SimpleStringProperty(szefImie);
        });

        szefNazwiskoColumn.setCellValueFactory(szefStringCellDataFeatures -> {
            Sekcja sekcja = szefStringCellDataFeatures.getValue();
            Optional<Redaktor> szef = redaktorDAO.get(sekcja.getSzefId());
            String szefNazwisko = szef.map(szefLambda -> szefLambda.getNazwisko()).orElse("");

            return new SimpleStringProperty(szefNazwisko);
        });

        ViewInitializer.addColumnsToTableView(sekcjaTableView, sekcjaIdColumn, nazwaColumn, szefIdColumn, szefImieColumn, szefNazwiskoColumn);
        ViewInitializer.setObservableListToTableView(sekcjaTableView, sekcjaObservableEntityListWrapper.getObservableList());
    }

    private boolean checkIfAnyFieldWasEdited(Sekcja sekcja, SekcjaFormValues sekcjaFormValues) {
        boolean wasEdited = false;

        if(!sekcja.getNazwa().equals(sekcjaFormValues.nazwa)) {
            wasEdited = true;
        }
        else if (sekcja.getSzefId() != sekcjaFormValues.szef.getRedaktorId()) {
            wasEdited = true;
        }

        return wasEdited;
    }

    private SekcjaFormValues readSekcjaForm() {

        SekcjaFormValues sekcjaFormValues = new SekcjaFormValues();

        sekcjaFormValues.nazwa = nazwaTextField.getText();
        sekcjaFormValues.szef = szefChoiceBox.getValue();

        return sekcjaFormValues;
    }

    class SekcjaFormValues {
        private String nazwa;
        private Redaktor szef;
    }
}
