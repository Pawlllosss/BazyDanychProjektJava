package redaktor.controller.helper.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ObservableListWrapper<T> {

    protected ObservableList<T> observableList;

    public abstract void updateObservableList();

    public ObservableListWrapper() {
        this.observableList = FXCollections.observableArrayList();
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }
}
