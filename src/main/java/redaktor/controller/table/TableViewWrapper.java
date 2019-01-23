package redaktor.controller.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;

public abstract class TableViewWrapper<T> {
    protected TableView<T> tableView;

    public abstract void initialize(ObservableEntityListWrapper<T> observableEntityListWrapper);

    public TableViewWrapper(TableView<T> tableView) {
        this.tableView = tableView;
    }

    public T getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    protected void addColumnsToTableView(TableColumn<T, ?>... tableColumns) {
        tableView.getColumns().addAll(tableColumns);
    }

    protected void setObservableListToTableView(ObservableList<T> observableList) {
        tableView.setItems(observableList);
    }
}
