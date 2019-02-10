package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;

public class RedaktorTableViewWrapper extends TableViewWrapper<Redaktor> {

    public RedaktorTableViewWrapper(TableView<Redaktor> tableView) {
        super(tableView);
        createAndAddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<Redaktor> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Redaktor> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndAddColumnsToTableView() {
        SekcjaDAO sekcjaDAO = SekcjaDAO.getInstance();

        TableColumn<Redaktor, Long> redaktorIdColumn = ViewInitializer.createColumn("Id redaktora", "redaktorId", 80);
        TableColumn<Redaktor, String> imieColumn = ViewInitializer.createColumn("Imie", "imie", 50);
        TableColumn<Redaktor, String> nazwiskoColumn = ViewInitializer.createColumn("Nazwisko", "nazwisko", 50);
        TableColumn<Redaktor, String> sekcjaNazwaColumn = new TableColumn<>("Nazwa sekcji");

        sekcjaNazwaColumn.setCellValueFactory(redaktorStringCellDataFeatures -> {
            Redaktor redaktor = redaktorStringCellDataFeatures.getValue();
            Optional<Sekcja> sekcja = sekcjaDAO.get(redaktor.getSekcjaId());
            String sekcjaNazwa = sekcja.map(sekcjaLambda -> sekcjaLambda.getNazwa()).orElse(null);

            return new SimpleStringProperty(sekcjaNazwa);
        });

        addColumnsToTableView(redaktorIdColumn, imieColumn, nazwiskoColumn, sekcjaNazwaColumn);
    }

}
