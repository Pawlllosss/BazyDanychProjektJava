package redaktor.controller;

import javafx.collections.ObservableList;

public interface ValueObjectController<T> {
    //TODO: need Java8...
//    static <T> ObservableList<T> getObservableList();
    void setItemsFromOtherControllers();
}
