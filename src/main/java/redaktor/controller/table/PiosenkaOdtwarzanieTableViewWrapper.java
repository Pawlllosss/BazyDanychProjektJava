package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.AudycjaDAO;
import redaktor.DAO.PiosenkaDAO;
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.StudioDAO;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.controller.observable.ObservableNoUpdateArgumentsListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Audycja;
import redaktor.model.Piosenka;
import redaktor.model.PiosenkaOdtwarzanie;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

public class PiosenkaOdtwarzanieTableViewWrapper extends TableViewWrapper<PiosenkaOdtwarzanie> {
    public PiosenkaOdtwarzanieTableViewWrapper(TableView<PiosenkaOdtwarzanie> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<PiosenkaOdtwarzanie> observableEntityListWrapper) {
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<PiosenkaOdtwarzanie> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        PiosenkaDAO piosenkaDAO = PiosenkaDAO.getInstance();

        TableColumn<PiosenkaOdtwarzanie, Timestamp> czasOdtworzeniaColumn = ViewInitializer.createColumn("Czas odtworzenia", "czasOdtworzenia", 140);

        TableColumn<PiosenkaOdtwarzanie, String> tytulWykonawcaColumn = new TableColumn<>("Tytuł - wykonawca");
        tytulWykonawcaColumn.setMinWidth(180);

        tytulWykonawcaColumn.setCellValueFactory(audycjaStringCellDataFeatures -> {
            PiosenkaOdtwarzanie piosenkaOdtwarzanie = audycjaStringCellDataFeatures.getValue();

            Optional<Piosenka> piosenka = piosenkaDAO.get(piosenkaOdtwarzanie.getPiosenkaId());
            String tytulWykonawca = piosenka.map(piosenkaLambda -> piosenkaLambda.getTytul() + " - " + piosenkaLambda.getWykonawca()).orElse("");

            return new SimpleStringProperty(tytulWykonawca);
        });


        addColumnsToTableView(tytulWykonawcaColumn, czasOdtworzeniaColumn);
    }
}
