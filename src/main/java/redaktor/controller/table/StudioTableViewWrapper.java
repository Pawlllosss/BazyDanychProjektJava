package redaktor.controller.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.helper.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Studio;


public class StudioTableViewWrapper extends TableViewWrapper<Studio> {
    public StudioTableViewWrapper(TableView<Studio> tableView) {
        super(tableView);
    }

    @Override
    public void initialize(ObservableListWrapper<Studio> observableEntityListWrapper) {
        TableColumn<Studio, Long> studioIdColumn = ViewInitializer.createColumn("Id studia", "studioId", 80);
        TableColumn<Studio, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 50);
        TableColumn<Studio, Long> nrPokojuColumn = ViewInitializer.createColumn("Nr pokoju", "nrPokoju", 50);

        addColumnsToTableView(studioIdColumn, nazwaColumn, nrPokojuColumn);
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }
}
