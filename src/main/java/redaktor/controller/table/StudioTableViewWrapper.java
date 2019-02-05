package redaktor.controller.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Studio;


public class StudioTableViewWrapper extends TableViewWrapper<Studio> {
    public StudioTableViewWrapper(TableView<Studio> tableView) {
        super(tableView);
        createAndAddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<Studio> observableEntityListWrapper) {
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Studio> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndAddColumnsToTableView() {
        TableColumn<Studio, Long> studioIdColumn = ViewInitializer.createColumn("Id studia", "studioId", 80);
        TableColumn<Studio, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Studio, Long> nrPokojuColumn = ViewInitializer.createColumn("Nr pokoju", "nrPokoju", 50);

        addColumnsToTableView(studioIdColumn, nazwaColumn, nrPokojuColumn);
    }
}
