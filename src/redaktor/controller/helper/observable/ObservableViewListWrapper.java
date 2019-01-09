package redaktor.controller.helper.observable;

import redaktor.controller.helper.observable.update.ObservableListUpdater;

public class ObservableViewListWrapper<T> extends ObservableListWrapper<T> {

    private ObservableListUpdater<T> observableListUpdater;

    public ObservableViewListWrapper(ObservableListUpdater<T> observableListUpdater) {
        super();
        this.observableListUpdater = observableListUpdater;

        updateObservableList();
    }

    @Override
    public void updateObservableList() {
        observableListUpdater.executeUpdate(observableList);
    }
}
