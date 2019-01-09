package redaktor.controller.helper.observable;

import javafx.collections.FXCollections;
import redaktor.DAO.DAO;

import java.util.List;

public class ObservableEntityListWrapper<T> extends ObservableListWrapper<T> {

    private DAO<T> dao;

    public ObservableEntityListWrapper(DAO<T> dao) {
        super();
        this.observableList = FXCollections.observableArrayList();
        this.dao = dao;

        updateObservableList();
    }

    @Override
    public void updateObservableList() {
        List<T> valueObjects = dao.getAll();
        observableList.setAll(valueObjects);
    }
}
