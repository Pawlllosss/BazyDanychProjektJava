package redaktor.controller;

import javafx.collections.ObservableList;

public interface EntityController<T> {
    //TODO: need Java8...
//    static <T> ObservableList<T> getObservableList();
    void setItemsFromOtherControllers();
}
