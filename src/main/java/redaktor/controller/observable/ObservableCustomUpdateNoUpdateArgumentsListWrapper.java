package redaktor.controller.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

public class ObservableCustomUpdateNoUpdateArgumentsListWrapper<T> implements ObservableNoUpdateArgumentsListWrapper<T> {
    ObservableList<T> observableList;
    private Consumer<ObservableList<T>> observableListUpdater;

    public ObservableCustomUpdateNoUpdateArgumentsListWrapper(Consumer<ObservableList<T>> observableListUpdater) {
        this.observableList = FXCollections.observableArrayList();
        this.observableListUpdater = observableListUpdater;

        updateObservableList();
    }

    @Override
    public ObservableList<T> getObservableList() {
        return observableList;
    }

    @Override
    public void updateObservableList() {
        observableListUpdater.accept(observableList);
    }
}
