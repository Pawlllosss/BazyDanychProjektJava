package redaktor.controller.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.helper.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Piosenka;
import redaktor.model.Studio;


public class PiosenkaTableViewWrapper extends TableViewWrapper<Piosenka> {
    public PiosenkaTableViewWrapper(TableView<Piosenka> tableView) {
        super(tableView);
    }

    @Override
    public void initialize(ObservableListWrapper<Piosenka> observableEntityListWrapper) {
        TableColumn<Piosenka, Long> piosenkaIdColumn = ViewInitializer.createColumn("Id piosenki", "piosenkaId", 60);
        TableColumn<Piosenka, String> tytulColumn = ViewInitializer.createColumn("Tytuł", "tytul", 120);
        TableColumn<Piosenka, String> wykonawcaColumn = ViewInitializer.createColumn("Wykonawca", "wykonawca", 120);

        addColumnsToTableView(piosenkaIdColumn, tytulColumn, wykonawcaColumn);
        setObservableListToTableView(observableEntityListWrapper.getObservableList());
    }
}
