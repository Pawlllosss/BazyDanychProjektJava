package redaktor.controller.form;

import javafx.scene.control.TableView;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.table.TableViewHelper;

public class FormLoader {
    public static <T> void tryToLoadValuesIntoForm(TableView<T> tableView, Form<T> form, String notSelectedErrorMessage) {
        T entity = TableViewHelper.getSelectedItem(tableView);

        if(entity != null) {
            form.loadValuesIntoForm(entity);
        }
        else {
            WarningAlert warningAlert = new WarningAlert(notSelectedErrorMessage);
            warningAlert.showAndWait();
        }
    }
}
