package redaktor.controller.helper.crud;

import redaktor.DAO.DAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;
import redaktor.controller.table.TableViewWrapper;

import java.util.function.Function;

public class EntityDeleter {
    public static <T> void tryToDeleteEntity(TableViewWrapper<T> entityTable, DAO<T> dao, ObservableEntityNoUpdateArgumentsListWrapper<T> observableEntityListWrapper, Function<T, Long> idRetriever) {
        T entity = entityTable.getSelectedItem();

        if(entity != null) {
            long entityId = idRetriever.apply(entity);
            dao.delete(entityId);
            observableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Nie wybrano elementu!");
            warningAlert.showAndWait();
        }
    }
}
