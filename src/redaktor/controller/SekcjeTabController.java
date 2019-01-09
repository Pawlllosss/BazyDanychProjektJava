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
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.table.TableViewHelper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.display.RedaktorChoiceBoxDisplayNameRetriever;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.view.AlertBox;


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

    public SekcjeTabController() {
        System.out.println("Sekcjetab constr");
    }

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

    //TODO: wait for Java8...
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

    //TODO: add on cascade delete to database
    @FXML
    private void deleteSekcja() {

        Sekcja sekcja = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcja != null) {
            long sekcjaId = sekcja.getSekcjaId();
            sekcjaDAO.delete(sekcjaId);

            sekcjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano sekcji!");
            alertBox.show();
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
                AlertBox alertBox = new AlertBox("Nie zmodyfikowano żadnych pól!");
                alertBox.show();
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
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano sekcji!");
            alertBox.show();
        }
    }

    @FXML
    private void loadSekcjaEditForm() {
        Sekcja sekcja = TableViewHelper.getSelectedItem(sekcjaTableView);

        if(sekcja != null) {
            String nazwa = sekcja.getNazwa();
            long szefId = sekcja.getSekcjaId();
            Redaktor szef = redaktorDAO.get(szefId);

            nazwaTextField.setText(nazwa);
            szefChoiceBox.setValue(szef);

            sekcjaObservableEntityListWrapper.updateObservableList();
        }
        else {
            //TODO: in java8 I could use Alert
            AlertBox alertBox = new AlertBox("Nie wybrano sekcji!");
            alertBox.show();
        }
    }

    //TODO: handle redactors
    private void initializeSekcjaTableView() {
        TableColumn<Sekcja, Long> sekcjaIdColumn = ViewInitializer.createColumn("Id sekcja", "sekcjaId", 80);
        TableColumn<Sekcja, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Sekcja, String> szefIdColumn = ViewInitializer.createColumn("Id szef", "szefId", 50);
        TableColumn<Sekcja, String> szefImieColumn = new TableColumn<>("Imie szefa");
        TableColumn<Sekcja, String> szefNazwiskoColumn = new TableColumn<>("Nazwisko szefa");

        szefImieColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sekcja, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sekcja, String> szefStringCellDataFeatures) {
                Sekcja sekcja = szefStringCellDataFeatures.getValue();
                Redaktor szef = redaktorDAO.get(sekcja.getSzefId());

                String szefImie = "";

                //TODO: use optionals
                if(szef != null) {
                    szefImie = szef.getImie();
                }

                return new SimpleStringProperty(szefImie);
            }
        });

        szefNazwiskoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sekcja, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sekcja, String> szefStringCellDataFeatures) {
                Sekcja sekcja = szefStringCellDataFeatures.getValue();
                Redaktor szef = redaktorDAO.get(sekcja.getSzefId());
                String szefNazwisko = "";

                //TODO: use optionals
                if(szef != null) {
                    szefNazwisko = szef.getNazwisko();
                }

                return new SimpleStringProperty(szefNazwisko);
            }
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
