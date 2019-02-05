package redaktor.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import redaktor.DAO.AudycjaDAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.observable.PiosenkaOdtwarzanieListWrapper;
import redaktor.controller.table.AudycjaTableViewWrapper;
import redaktor.controller.table.PiosenkaOdtwarzanieTableViewWrapper;
import redaktor.controller.table.PiosenkaTableViewWrapper;
import redaktor.model.Audycja;
import redaktor.model.Piosenka;
import redaktor.model.PiosenkaOdtwarzanie;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudycjaPiosenkiTabController implements EntityController<PiosenkaOdtwarzanie>{
    private AudycjaDAO audycjaDAO;

    private AudycjaTableViewWrapper audycjaTableViewWrapper;
    private PiosenkaTableViewWrapper piosenkaTableViewWrapper;
    private PiosenkaOdtwarzanieTableViewWrapper audycjaAssignedPiosenkiTableViewWrapper;

    private PiosenkaOdtwarzanieListWrapper audycjaAssignedPiosenkiObservableListWrapper;

    @FXML
    private TableView<Audycja> audycjaTableView;
    @FXML
    private TableView<Piosenka> piosenkaTableView;
    @FXML
    private TableView<PiosenkaOdtwarzanie> audycjaAssignedPiosenkiPiosenkiTableView;
    @FXML
    private TextField czasOdtworzeniaTextField;

    @FXML
    private void initialize() {
        audycjaDAO = AudycjaDAO.getInstance();

        audycjaTableViewWrapper = new AudycjaTableViewWrapper(audycjaTableView);
        piosenkaTableViewWrapper = new PiosenkaTableViewWrapper(piosenkaTableView);

        audycjaAssignedPiosenkiObservableListWrapper = new PiosenkaOdtwarzanieListWrapper(audycjaDAO);
        audycjaAssignedPiosenkiTableViewWrapper = new PiosenkaOdtwarzanieTableViewWrapper(audycjaAssignedPiosenkiPiosenkiTableView);
        audycjaAssignedPiosenkiTableViewWrapper.initialize(audycjaAssignedPiosenkiObservableListWrapper);

        addTimeCorrectionCheckForTextField(czasOdtworzeniaTextField);

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
        Audycja audycja = audycjaTableViewWrapper.getSelectedItem();
        Piosenka piosenka = piosenkaTableViewWrapper.getSelectedItem();

        if(audycja != null && piosenka != null) {
            long audycjaId = audycja.getAudycjaId();
            long piosenkaId = piosenka.getPiosenkaId();
            String czasOdtworzeniaFromTextField = czasOdtworzeniaTextField.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            try {
                Date date = sdf.parse(czasOdtworzeniaFromTextField);
                Time czasOdtworzenia = new Time(date.getTime());

                PiosenkaOdtwarzanie piosenkaOdtwarzanie = new PiosenkaOdtwarzanie(0l, czasOdtworzenia, piosenkaId, audycjaId);
                audycjaDAO.assignPiosenkaToAudycja(piosenkaOdtwarzanie);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            WarningAlert warningAlert = new WarningAlert("Należy zaznaczyć audycję i piosenkę!");
            warningAlert.showAndWait();
        }
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

    //for form in future
    public String getCzasOdtwarzaniaFromTextField() {
        return czasOdtworzeniaTextField.getText();
    }
}
