package redaktor.controller.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Piosenka;


public class PiosenkaTableViewWrapper extends TableViewWrapper<Piosenka> {
    public PiosenkaTableViewWrapper(TableView<Piosenka> tableView) {
        super(tableView);
        createAndAddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<Piosenka> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Piosenka> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndAddColumnsToTableView() {
        TableColumn<Piosenka, Long> piosenkaIdColumn = ViewInitializer.createColumn("Id piosenki", "piosenkaId", 60);
        TableColumn<Piosenka, String> tytulColumn = ViewInitializer.createColumn("Tytuł", "tytul", 120);
        TableColumn<Piosenka, String> wykonawcaColumn = ViewInitializer.createColumn("Wykonawca", "wykonawca", 120);

        addColumnsToTableView(piosenkaIdColumn, tytulColumn, wykonawcaColumn);
    }
}
