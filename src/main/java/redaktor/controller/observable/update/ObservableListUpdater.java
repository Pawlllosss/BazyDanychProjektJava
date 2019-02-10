package redaktor.controller.observable.update;

import javafx.collections.ObservableList;

public interface ObservableListUpdater<T> {
    void executeUpdate(ObservableList<T> observableList);
}
