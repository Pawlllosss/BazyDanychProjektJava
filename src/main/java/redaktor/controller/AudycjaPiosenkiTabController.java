package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.AudycjaDAO;
import redaktor.DAO.PiosenkaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.observable.ObservableCustomUpdateNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.PiosenkaOdtwarzanieListWrapper;
import redaktor.controller.table.AudycjaTableViewWrapper;
import redaktor.controller.table.PiosenkaTableViewWrapper;
import redaktor.model.Audycja;
import redaktor.model.Piosenka;
import redaktor.model.PiosenkaOdtwarzanie;

import java.util.List;

public class AudycjaPiosenkiTabController implements EntityController<PiosenkaOdtwarzanie>{
    private AudycjaDAO audycjaDAO;
    private PiosenkaDAO piosenkaDAO;

//    private PiosenkaOdtwarzanieForm piosenkaOdtwarzanieForm;

    private AudycjaTableViewWrapper audycjaTableViewWrapper;
    private PiosenkaTableViewWrapper piosenkaTableViewWrapper;
//    private PiosenkaOdtwarzanieTableViewWrapper audycjaAssignedPiosenkiTableViewWrapper; //? czy ma to sens

    private ObservableNoUpdateArgumentsListWrapper<Audycja> audycjaObservableNoUpdateArgumentsListWrapper;
    private ObservableNoUpdateArgumentsListWrapper<Piosenka> piosenkaObservableNoUpdateArgumentsListWrapper;
    private PiosenkaOdtwarzanieListWrapper audycjaAssignedPiosenkiObservableListWrapper;

    @FXML
    private TableView<Audycja> audycjaTableView;
    @FXML
    private TableView<Piosenka> piosenkaTableView;
    @FXML
    private TableView<Piosenka> chosenAudycjaPiosenkiTableView;
    @FXML
    private TextField czasOdtwarzaniaTextField;

    @FXML
    private void initialize() {
        audycjaDAO = AudycjaDAO.getInstance();
        piosenkaDAO = PiosenkaDAO.getInstance();

        audycjaObservableNoUpdateArgumentsListWrapper = new ObservableEntityNoUpdateArgumentsListWrapper<>(audycjaDAO);

        audycjaTableViewWrapper = new AudycjaTableViewWrapper(audycjaTableView);
        piosenkaTableViewWrapper = new PiosenkaTableViewWrapper(piosenkaTableView);

        audycjaAssignedPiosenkiObservableListWrapper = new PiosenkaOdtwarzanieListWrapper(audycjaDAO);
//        audycjaAssignedPiosenkiTableViewWrapper = new PiosenkaOdtwarzanieTableViewWrapper(audycjaAssignedPiosenkiObservableListWrapper);


        addTimeCorrectionCheckForTextField(czasOdtwarzaniaTextField);

        //is it needed here?
//        piosenkaOdtwarzanieForm = new PiosenkaOdtwarzanieForm(this);

        MainController.addEntityController(this);
    }


    @Override
    public void setItemsFromOtherControllers() {
        ObservableList<Audycja> audycjaObservableList = AudycjaTabController.getObservableList();
        audycjaTableViewWrapper.initialize(audycjaObservableList);

        ObservableList<Piosenka> piosenkaObservableList = PiosenkaTabController.getObservableList();
        piosenkaTableViewWrapper.initialize(piosenkaObservableList);
    }

    @FXML
    private void assignPiosenkaToAudycja() {

    }

    @FXML
    private void deleteSongFromAudycja() {

    }

    @FXML
    private void showSongsForAudycja() {
        Audycja audycja = audycjaTableViewWrapper.getSelectedItem();

        if(audycja != null) {
            long audycjaId = audycja.getAudycjaId();
            audycjaAssignedPiosenkiObservableListWrapper.updateObservableList(audycjaId);
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano audycji!");
            warningAlert.showAndWait();
        }
    }

    private void addTimeCorrectionCheckForTextField(TextField textField) {
        textField.textProperty().addListener((observableValue, oldString, newString) -> {
            if (!newString.matches("(\\d{0,2}:?){0,3}")) {
                textField.setText(oldString);
            }
        });
    }

    public String getCzasOdtwarzaniaFromTextField() {
        return czasOdtwarzaniaTextField.getText();
    }

    public void setCzasOdtwarzanieToTextField(String czasOdtwarzania) {
        czasOdtwarzaniaTextField.setText(czasOdtwarzania);
    }
}
