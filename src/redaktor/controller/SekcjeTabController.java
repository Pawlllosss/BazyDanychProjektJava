package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.initialize.DisplayNameRetriever;
import redaktor.initialize.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.ObservableListCreator;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;


public class SekcjeTabController implements ValueObjectController<Sekcja> {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    private static ObservableListWrapper<Sekcja> sekcjaObservableListWrapper;

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
        sekcjaObservableListWrapper = new ObservableListWrapper<>(sekcjaDAO);

        initializeSekcjaTableView();
        ViewInitializer.initializeChoiceBox(szefChoiceBox, new DisplayNameRetriever<Redaktor>() {
            @Override
            public String getName(Redaktor redaktor) {
                String redaktorName = redaktor.getImie() + " " + redaktor.getNazwisko();
                return redaktorName;
            }
        });

        MainController.addValueObjectController(this);
    }

    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Redaktor> redaktorObservableList = RedaktorzyTabController.getObservableList();
        szefChoiceBox.setItems(redaktorObservableList);
    }

    //TODO: wait for Java8...
//    @Override
    public static ObservableList<Sekcja> getObservableList() {
        return sekcjaObservableListWrapper.getObservableList();
    }

    @FXML
    private void addSekcja() {
        Redaktor szef = szefChoiceBox.getValue();
        String nazwa =  nazwaTextField.getText();
        long szefId = szef.getRedaktorId();

        Sekcja sekcja = new Sekcja(0, nazwa, szefId);
        sekcjaDAO.save(sekcja);
        sekcjaObservableListWrapper.updateObservableList();
    }

    private void initializeSekcjaTableView() {
        TableColumn<Sekcja, Long> sekcjaIdColumn = ViewInitializer.createColumn("Id sekcja", "sekcjaId", 80);
        TableColumn<Sekcja, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);

        ViewInitializer.addColumnsToTableView(sekcjaTableView, sekcjaIdColumn, nazwaColumn);
        ViewInitializer.setObservableListToTableView(sekcjaTableView, sekcjaObservableListWrapper.getObservableList());
    }

    private void updateSzefChoiceBox() {
        ObservableListCreator.updateChoiceBox(szefChoiceBox, redaktorDAO);
    }

}
