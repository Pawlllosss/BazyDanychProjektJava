package redaktor.controller.helper;

import javafx.scene.control.TableView;

public class TableViewHelper {

    //TODO: could use Optionals
    public static <T> T getSelectedItem(TableView<T> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
