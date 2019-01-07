package redaktor.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.helper.ObservableListWrapper;
import redaktor.controller.helper.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.SekcjaChoiceBoxDisplayNameRetriever;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.view.AlertBox;


public class RedaktorzyTabController implements EntityController<Redaktor> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private static ObservableListWrapper<Redaktor> redaktorObservableListWrapper;

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
        redaktorObservableListWrapper = new ObservableListWrapper<>(redaktorDAO);

        initializeRedaktorTableView();
        //TODO: in Java8 I could use lamba and functional features...
        ViewInitializer.initializeChoiceBox(sekcjaChoiceBox, new SekcjaChoiceBoxDisplayNameRetriever());

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
        return redaktorObservableListWrapper.getObservableList();
    }

    @FXML
    private void addRedactor() {
        RedaktorFormValues redaktorFormValues = readRedaktorForm();

        //TODO: add some field validation and maybe triggers?
        Sekcja sekcja = redaktorFormValues.sekcja;
        String imie = redaktorFormValues.imie;
        String nazwisko =  redaktorFormValues.nazwisko;
        long sekcjaId = sekcja.getSekcjaId();

        Redaktor redaktor = new Redaktor(0, imie, nazwisko, sekcjaId);
        redaktorDAO.save(redaktor);
        redaktorObservableListWrapper.updateObservableList();
    }

    @FXML
    private void deleteRedaktor() {
        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            long redaktorId = redaktor.getRedaktorId();
            redaktorDAO.delete(redaktorId);

            redaktorObservableListWrapper.updateObservableList();
        }
        else {
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano redaktora!");
            alertBox.show();
        }
    }

    @FXML
    private void editRedaktor() {

        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            long redaktorId = redaktor.getRedaktorId();

            RedaktorFormValues redaktorFormValues = readRedaktorForm();

            //TODO: validate
            if(!checkIfAnyFieldWasEdited(redaktor, redaktorFormValues)) {
                AlertBox alertBox = new AlertBox("Nie zmodyfikowano żadnych pól!");
                alertBox.show();
            }
            else {
                String imie = redaktorFormValues.imie;
                String nazwisko = redaktorFormValues.nazwisko;
                long sekcjaId = redaktorFormValues.sekcja.getSekcjaId();
                Redaktor editedRedaktor = new Redaktor(redaktorId, imie, nazwisko, sekcjaId);

                redaktorDAO.update(redaktor, editedRedaktor);
                redaktorObservableListWrapper.updateObservableList();
            }

        }
        else {
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano redaktora!");
            alertBox.show();
        }
    }

    @FXML
    private void loadRedaktorEditForm() {

        Redaktor redaktor = TableViewHelper.getSelectedItem(redaktorTableView);

        if(redaktor != null) {
            String imie = redaktor.getImie();
            String nazwisko = redaktor.getNazwisko();
            long sekcjaId = redaktor.getSekcjaId();
            Sekcja sekcja = sekcjaDAO.get(sekcjaId);

            imieTextField.setText(imie);
            nazwiskoTextField.setText(nazwisko);


            //TODO: what if section was deleted?
            //TODO: doesn't work....
            sekcjaChoiceBox.setValue(sekcja);

            redaktorObservableListWrapper.updateObservableList();
        }
        else {
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano redaktora!");
            alertBox.show();
        }
    }

    private void initializeRedaktorTableView() {
        TableColumn<Redaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = ViewInitializer.createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = ViewInitializer.createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        //TODO: java7 doesn't have lambdas...
        sekcjaNazwaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Redaktor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Redaktor, String> redaktorStringCellDataFeatures) {
                Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
                Sekcja sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
                String sekcjaNazwa = sekcja.getNazwa();

                return new SimpleStringProperty(sekcjaNazwa);
            }
        });

        ViewInitializer.addColumnsToTableView(redaktorTableView, redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
        ViewInitializer.setObservableListToTableView(redaktorTableView, redaktorObservableListWrapper.getObservableList());
    }

    private boolean checkIfAnyFieldWasEdited(Redaktor redaktor, RedaktorFormValues redaktorFormValues) {
        boolean wasEdited = false;

        if(redaktor.getImie().equals(redaktorFormValues.imie)) {
            wasEdited = true;
        }
        else if (redaktor.getNazwisko().equals(redaktorFormValues.nazwisko)) {
            wasEdited = true;
        }
        else if (redaktor.getSekcjaId() == redaktorFormValues.sekcja.getSekcjaId()) {
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