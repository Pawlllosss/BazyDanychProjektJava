package redaktor.controller.observable;

import javafx.collections.ObservableList;

public interface ObservableListWrapper<T> {
    ObservableList<T> getObservableList();
}
