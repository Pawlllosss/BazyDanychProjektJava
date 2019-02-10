package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Sekcja;
import redaktor.model.program.Program;

import java.util.Optional;

public class ProgramTableViewWrapper extends TableViewWrapper<Program> {

    public ProgramTableViewWrapper(TableView<Program> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<Program> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Program> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        SekcjaDAO sekcjaDAO = SekcjaDAO.getInstance();

        TableColumn<Program, Long> programIdColumn = ViewInitializer.createColumn("Id programu", "programId", 50);
        TableColumn<Program, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 90);
        TableColumn<Program, String> opisColumn = ViewInitializer.createColumn("Opis", "opis", 100);
        TableColumn<Program, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        sekcjaNazwaColumn.setCellValueFactory(programStringCellDataFeatures -> {
                    Program program = programStringCellDataFeatures.getValue();
                    Optional<Sekcja> sekcja = sekcjaDAO.get(program.getSekcjaId());
                    String sekcjaNazwa = sekcja.map(Sekcja::getNazwa).orElse("");

                    return new SimpleStringProperty(sekcjaNazwa);
                });

        addColumnsToTableView(programIdColumn, nazwaColumn, opisColumn, sekcjaNazwaColumn);
    }
}
