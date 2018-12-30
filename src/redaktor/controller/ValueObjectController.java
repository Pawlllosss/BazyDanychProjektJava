package redaktor.controller;

import javafx.collections.ObservableList;

public interface ValueObjectController<T> {
    ObservableList<T> getObservableList();
}
