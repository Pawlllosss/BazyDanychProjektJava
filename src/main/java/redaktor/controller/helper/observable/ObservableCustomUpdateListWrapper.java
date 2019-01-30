package redaktor.controller.helper.observable;

import javafx.collections.ObservableList;

import java.util.function.Consumer;

public class ObservableCustomUpdateListWrapper<T> extends ObservableListWrapper<T> {

    private Consumer<ObservableList<T>> observableListUpdater;

    public ObservableCustomUpdateListWrapper(Consumer<ObservableList<T>> observableListUpdater) {
        super();
        this.observableListUpdater = observableListUpdater;

        updateObservableList();
    }

    @Override
    public void updateObservableList() {
        observableListUpdater.accept(observableList);
    }
}
