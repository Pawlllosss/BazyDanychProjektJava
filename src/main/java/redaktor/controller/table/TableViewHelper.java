package redaktor.controller.table;

import javafx.scene.control.TableView;

final public class TableViewHelper {

    public static <T> T getSelectedItem(TableView<T> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
