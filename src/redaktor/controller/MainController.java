package redaktor.controller;

//TODO: create tabs
//TODO: refresh redactor list automatically

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private RedaktorzyTabController redaktorzyTabController;

    @FXML
    private SekcjeTabController sekcjeTabController;

    @FXML
    private void initialize() {
        System.out.println("MainController initialized");
    }
}
