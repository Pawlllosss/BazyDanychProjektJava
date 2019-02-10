package redaktor.controller.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import redaktor.DAO.RedaktorDAO;
import redaktor.controller.observable.ObservableListWrapper;
import redaktor.initialize.ViewInitializer;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;
import java.util.function.Function;

public class SekcjaTableViewWrapper extends TableViewWrapper<Sekcja> {

    public SekcjaTableViewWrapper(TableView<Sekcja> tableView) {
        super(tableView);
        createAndaddColumnsToTableView();
    }

    @Override
    public void initialize(ObservableListWrapper<Sekcja> observableListWrapper) {
        setObservableListToTableView(observableListWrapper.getObservableList());
    }

    @Override
    public void initialize(ObservableList<Sekcja> observableList) {
        setObservableListToTableView(observableList);
    }

    private void createAndaddColumnsToTableView() {
        RedaktorDAO redaktorDAO = RedaktorDAO.getInstance();

        TableColumn<Sekcja, Long> sekcjaIdColumn = ViewInitializer.createColumn("Id sekcja", "sekcjaId", 40);
        TableColumn<Sekcja, String> nazwaColumn = ViewInitializer.createColumn("Nazwa", "nazwa", 100);
        TableColumn<Sekcja, String> szefIdColumn = ViewInitializer.createColumn("Id szef", "szefId", 40);
        TableColumn<Sekcja, String> szefImieColumn = new TableColumn<>("Imie szefa");
        TableColumn<Sekcja, String> szefNazwiskoColumn = new TableColumn<>("Nazwisko szefa");

        szefImieColumn.setCellValueFactory(szefStringCellDataFeatures -> {
            Sekcja sekcja = szefStringCellDataFeatures.getValue();
            Optional<Redaktor> szef = redaktorDAO.get(sekcja.getSzefId());
            String szefImie = retrieveRedaktorName(szef, Redaktor::getImie);

            return new SimpleStringProperty(szefImie);
        });

        szefNazwiskoColumn.setCellValueFactory(szefStringCellDataFeatures -> {
            Sekcja sekcja = szefStringCellDataFeatures.getValue();
            Optional<Redaktor> szef = redaktorDAO.get(sekcja.getSzefId());
            String szefNazwisko = retrieveRedaktorName(szef, Redaktor::getNazwisko);

            return new SimpleStringProperty(szefNazwisko);
        });

        addColumnsToTableView(sekcjaIdColumn, nazwaColumn, szefIdColumn, szefImieColumn, szefNazwiskoColumn);
    }

    private String retrieveRedaktorName(Optional<Redaktor> redaktor, Function<Redaktor, String> nameRetriever) {
        return redaktor.map(nameRetriever).orElse("");
    }
}
