package redaktor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.initialize.DisplayNameRetriever;
import redaktor.initialize.ViewInitializer;
import redaktor.initialize.ViewUpdater;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;


public class SekcjeTabController {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

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

        initializeSekcjaTableView();
        ViewInitializer.initializeChoiceBox(szefChoiceBox, new DisplayNameRetriever<Redaktor>() {
            @Override
            public String getName(Redaktor redaktor) {
                String redaktorName = redaktor.getImie() + " " + redaktor.getNazwisko();
                return redaktorName;
            }
        });

        updateSekcjaTableView();
        updateSzefChoiceBox();
    }

    @FXML
    private void addSekcja() {
        Redaktor szef = szefChoiceBox.getValue();
        String nazwa =  nazwaTextField.getText();
        long szefId = szef.getRedaktorId();

        Sekcja sekcja = new Sekcja(0, nazwa, szefId);
        sekcjaDAO.save(sekcja);

        //TODO: use observer pattern
        updateSekcjaTableView();
    }

    private void initializeSekcjaTableView() {
        TableColumn<Sekcja, Long> sekcjaIdColumn = ViewInitializer.createColumn("Id sekcja", "sekcjaId", 80);
        TableColumn<Sekcja, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);

        ViewInitializer.addColumnsToTableView(sekcjaTableView, sekcjaIdColumn, nazwaColumn);
    }


    private void updateSekcjaTableView() {
        ViewUpdater.updateTableView(sekcjaTableView, sekcjaDAO);
    }

    private void updateSzefChoiceBox() {
        ViewUpdater.updateChoiceBox(szefChoiceBox, redaktorDAO);
    }

}
