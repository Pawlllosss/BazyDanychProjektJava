package redaktor.controller.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import redaktor.DAO.DAO;

import java.util.List;

public class ObservableEntityNoUpdateArgumentsListWrapper<T> implements ObservableNoUpdateArgumentsListWrapper<T> {
    ObservableList<T> observableList;
    private DAO<T> dao;

    public ObservableEntityNoUpdateArgumentsListWrapper(DAO<T> dao) {
        this.observableList = FXCollections.observableArrayList();
        this.dao = dao;

        updateObservableList();
    }

    @Override
    public ObservableList getObservableList() {
        return observableList;
    }

    @Override
    public void updateObservableList() {
        List<T> valueObjects = dao.getAll();
        observableList.setAll(valueObjects);
    }
}
