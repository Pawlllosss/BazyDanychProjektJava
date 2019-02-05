package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.ProgramDAO;
import redaktor.DAO.StudioDAO;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;
import redaktor.controller.helper.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Audycja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;

public class AudycjaTableViewWrapper extends TableViewWrapper<Audycja> {

    public AudycjaTableViewWrapper(TableView<Audycja> tableView) {
        super(tableView);
    }

    @Override
    public void initialize(ObservableListWrapper<Audycja> observableEntityListWrapper) {
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
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }
}
