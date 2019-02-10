package redaktor.controller.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.program.view.ProgramPrzypisanyRedaktor;

public class ProgramPrzypisanyRedaktorTableViewWrapper extends TableViewWrapper<ProgramPrzypisanyRedaktor> {

    public ProgramPrzypisanyRedaktorTableViewWrapper(TableView<ProgramPrzypisanyRedaktor> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<ProgramPrzypisanyRedaktor> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<ProgramPrzypisanyRedaktor> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        TableColumn<ProgramPrzypisanyRedaktor, Long> programIdColumn = ViewInitializer.createColumn("Id programu", "programId", 50);
        TableColumn<ProgramPrzypisanyRedaktor, String> programNazwaColumn = ViewInitializer.createColumn("Nazwa programu", "programNazwa", 120);
        TableColumn<ProgramPrzypisanyRedaktor, String> imieNazwiskoColumn = ViewInitializer.createColumn("Imie nazwisko", "imieNazwisko", 120);
        TableColumn<ProgramPrzypisanyRedaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 50);

        addColumnsToTableView(programIdColumn, programNazwaColumn, imieNazwiskoColumn, redaktorIdColumn);
    }
}
