package redaktor.controller.observable.update;

import javafx.collections.ObservableList;

//TODO: in java8 I could use lambdas
public interface ObservableListUpdater<T> {
    void executeUpdate(ObservableList<T> observableList);
}
