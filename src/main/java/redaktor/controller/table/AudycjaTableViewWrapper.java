package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.StudioDAO;
import redaktor.controller.observable.ObservableNoUpdateArgumentsListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Audycja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Time;
import java.util.Optional;

public class AudycjaTableViewWrapper extends TableViewWrapper<Audycja> {

    public AudycjaTableViewWrapper(TableView<Audycja> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableNoUpdateArgumentsListWrapper<Audycja> observableEntityListWrapper) {
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Audycja> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        ProgramDAO programDAO = ProgramDAO.getInstance();
        StudioDAO studioDAO = StudioDAO.getInstance();

        TableColumn<Audycja, Long> audycjaIdColumn = ViewInitializer.createColumn("Id audycji", "audycjaId", 40);
        TableColumn<Audycja, Time> godzinaPoczatek = ViewInitializer.createColumn("Data pocz.", "dataPoczatek", 160);
        TableColumn<Audycja, Time> godzinaKoniec = ViewInitializer.createColumn("Data kon.", "dataKoniec", 160);

        TableColumn<Audycja, String> programNazwaColumn = new TableColumn<>("Program");
        TableColumn<Audycja, String> studioNazwaColumn = new TableColumn<>("Studio");

        programNazwaColumn.setCellValueFactory(audycjaStringCellDataFeatures -> {
            Audycja audycja = audycjaStringCellDataFeatures.getValue();

            Optional<Program> program = programDAO.get(audycja.getProgramId());
            String programNazwa = program.map(programLambda -> programLambda.getNazwa()).orElse("");

            return new SimpleStringProperty(programNazwa);
        });

        studioNazwaColumn.setCellValueFactory(audycjaStringCellDataFeatures -> {
            Audycja audycja = audycjaStringCellDataFeatures.getValue();
            Optional<Studio> studio = studioDAO.get(audycja.getStudioId());
            String studioNazwa = studio.map(studioLambda -> studioLambda.getNazwa()).orElse("");

            return new SimpleStringProperty(studioNazwa);
        });

        addColumnsToTableView(audycjaIdColumn, programNazwaColumn, studioNazwaColumn, godzinaPoczatek, godzinaKoniec);
    }
}
