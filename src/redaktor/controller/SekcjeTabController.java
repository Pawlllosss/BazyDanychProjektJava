package redaktor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import redaktor.DAO.RedaktorDAO;
import redaktor.DAO.SekcjaDAO;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

public class SekcjeTabController {

    private RedaktorDAO redaktorDAO;
    private SekcjaDAO sekcjaDAO;

    @FXML
    private TableView<Redaktor> sekcjaTableView;

    @FXML
    private ChoiceBox<Sekcja> szefChoiceBox;


    @FXML
    private void initialize() {
        redaktorDAO = RedaktorDAO.getInstance();
        sekcjaDAO = SekcjaDAO.getInstance();

//        initializeSekcjaTableView();
//        initializeSzefChoiceBox();
//
//        updateSekcjaTableView();
//        updateSzefChoiceBox();

    }

    @FXML
    private void addSekcja() {

    }
}
