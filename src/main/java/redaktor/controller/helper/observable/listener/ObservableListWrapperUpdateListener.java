package redaktor.controller.helper.observable.listener;

import redaktor.controller.helper.observable.ObservableListWrapper;
import redaktor.controller.helper.observable.ObservableViewListWrapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ObservableListWrapperUpdateListener {
    private List<ObservableListWrapper<?>> observableViewListWrappers;

    public void appendObservableViewListWrappers(ObservableViewListWrapper<?> ... observableViewListWrappersToAdd) {
        observableViewListWrappers = new LinkedList<>();
        observableViewListWrappers.addAll(Arrays.asList(observableViewListWrappersToAdd));
    }

    public void updateLists() {
        for(ObservableListWrapper<?> observableListWrapper : observableViewListWrappers) {
            observableListWrapper.updateObservableList();
        }
    }

}
