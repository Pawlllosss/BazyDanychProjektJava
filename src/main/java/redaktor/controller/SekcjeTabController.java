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
import redaktor.controller.form.FormLoader;
import redaktor.controller.form.sekcja.SekcjaForm;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
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

    @FXML
    private TableView<Sekcja> sekcjaTableView;
    @FXML
    private ChoiceBox<Redaktor> szefChoiceBox;
    @FXML
    private TextField nazwaTextField;

    private static ObservableEntityListWrapper<Sekcja> sekcjaObservableEntityListWrapper;

    @FXML
    private void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();
        sekcjaObservableEntityListWrapper = new ObservableEntityListWrapper<>(sekcjaDAO);

        initializeSekcjaTableView();
        ViewInitializer.initializeChoiceBox(szefChoiceBox, new RedaktorChoiceBoxDisplayNameRetriever());

        sekcjaForm = new SekcjaForm(this);
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
        if(sekcjaForm.isFormCorrectlyFilled()) {
            Sekcja sekcja = sekcjaForm.readForm();
            sekcjaDAO.save(sekcja);
            sekcjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
            warningAlert.showAndWait();
        }
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
        Sekcja sekcjaToEdit = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcjaToEdit != null) {

            if(!sekcjaForm.isFormCorrectlyFilled()) {
                WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
                warningAlert.showAndWait();
                return;
            }

            if(!sekcjaForm.isFormDifferentFromEntity(sekcjaToEdit)) {
                WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
                warningAlert.showAndWait();
            }
            else {
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
}
