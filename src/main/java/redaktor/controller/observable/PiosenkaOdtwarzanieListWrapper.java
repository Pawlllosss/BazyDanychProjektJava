package redaktor.controller.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import redaktor.DAO.AudycjaDAO;
import redaktor.model.PiosenkaOdtwarzanie;

import java.util.List;

public class PiosenkaOdtwarzanieListWrapper implements ObservableListWrapper<PiosenkaOdtwarzanie> {
    ObservableList<PiosenkaOdtwarzanie> observableList;
    private AudycjaDAO dao;

    public PiosenkaOdtwarzanieListWrapper(AudycjaDAO dao) {
        this.observableList = FXCollections.observableArrayList();
        this.dao = dao;
    }

    @Override
    public ObservableList<PiosenkaOdtwarzanie> getObservableList() {
        return observableList;
    }

    public void updateObservableList(long audycjaId) {
        List<PiosenkaOdtwarzanie> piosenkasOdtwarzanie = dao.getPiosenkasForAudycja(audycjaId);
        observableList.setAll(piosenkasOdtwarzanie);
    }
}
