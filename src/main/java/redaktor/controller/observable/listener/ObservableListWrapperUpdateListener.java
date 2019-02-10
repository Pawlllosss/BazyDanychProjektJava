package redaktor.controller.observable.listener;

import redaktor.controller.observable.ObservableCustomUpdateNoUpdateArgumentsListWrapper;
import redaktor.controller.observable.ObservableNoUpdateArgumentsListWrapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ObservableListWrapperUpdateListener {
    private List<ObservableNoUpdateArgumentsListWrapper<?>> observableViewListWrappers;

    public void appendObservableViewListWrappers(ObservableCustomUpdateNoUpdateArgumentsListWrapper<?>... observableCustomUpdateListWrappersToAdd) {
        observableViewListWrappers = new LinkedList<>();
        observableViewListWrappers.addAll(Arrays.asList(observableCustomUpdateListWrappersToAdd));
    }

    public void updateLists() {
        for(ObservableNoUpdateArgumentsListWrapper<?> observableNoUpdateArgumentsListWrapper : observableViewListWrappers) {
            observableNoUpdateArgumentsListWrapper.updateObservableList();
        }
    }

}
