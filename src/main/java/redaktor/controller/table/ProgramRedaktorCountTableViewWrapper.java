package redaktor.controller.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.program.view.ProgramRedaktorCount;

public class ProgramRedaktorCountTableViewWrapper extends TableViewWrapper<ProgramRedaktorCount> {

    public ProgramRedaktorCountTableViewWrapper(TableView<ProgramRedaktorCount> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<ProgramRedaktorCount> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<ProgramRedaktorCount> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        TableColumn<ProgramRedaktorCount, Long> programIdColumn = ViewInitializer.createColumn("Id programu", "programId", 50);
        TableColumn<ProgramRedaktorCount, String> programNazwaColumn = ViewInitializer.createColumn("Nazwa programu", "programNazwa", 120);
        TableColumn<ProgramRedaktorCount, Long> redaktorCountColumn = ViewInitializer.createColumn("Liczba redaktorów", "redaktorCount", 120);

        addColumnsToTableView(programIdColumn, programNazwaColumn, redaktorCountColumn);
    }
}
