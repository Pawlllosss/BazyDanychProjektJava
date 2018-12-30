package redaktor.initialize;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ObservableListCreator {

//    public static <T> void updateChoiceBox(ChoiceBox<T> choiceBox, DAO<T> dao) {
//        //TODO: add some interface to provide method for retrieved data
//        List<T> DTOs = dao.getAll();
//        ObservableList<T> DTOsObservableList = createObservableListFromList(DTOs);
//        choiceBox.setItems(DTOsObservableList);
//    }

    private static <T> ObservableList<T> createObservableListFromList(List<T> list) {
        ObservableList<T> observableList = FXCollections.observableList(list);
        return observableList;
    }
}
