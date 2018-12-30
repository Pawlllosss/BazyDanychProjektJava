package redaktor.initialize;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import redaktor.DAO.DAO;

import java.util.List;

public class ViewUpdater {

    public static <T> void updateTableView(TableView<T> tableView, DAO<T> dao) {
        List<T> DTOs = dao.getAll();
        ObservableList<T> DTOsObservableList = createObservableListFromList(DTOs);
        tableView.setItems(DTOsObservableList);
    }

    public static <T> void updateChoiceBox(ChoiceBox<T> choiceBox, DAO<T> dao) {
        //TODO: add some interface to provide method for retrieved data
        List<T> DTOs = dao.getAll();
        ObservableList<T> DTOsObservableList = createObservableListFromList(DTOs);
        choiceBox.setItems(DTOsObservableList);
    }

    private static <T> ObservableList<T> createObservableListFromList(List<T> list) {
        ObservableList<T> observableList = FXCollections.observableList(list);
        return observableList;
    }
}
