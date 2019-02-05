package redaktor.controller.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ObservableNoUpdateArgumentsListWrapper<T> extends ObservableListWrapper{
    void updateObservableList();
}
