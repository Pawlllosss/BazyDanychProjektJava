package redaktor.controller.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import redaktor.DAO.DAO;

import java.util.List;

public class ObservableListWrapper<T> {

    private ObservableList<T> observableList;
    private DAO<T> dao;

    public ObservableListWrapper(DAO<T> dao) {
        this.observableList = FXCollections.observableArrayList();
        this.dao = dao;

        updateObservableList();
    }

    public void updateObservableList() {
        List<T> valueObjects = dao.getAll();
        System.out.println(valueObjects);
        observableList.setAll(valueObjects);
    }

    public ObservableList<T> getObservableList() {
        return observableList;
    }
}
