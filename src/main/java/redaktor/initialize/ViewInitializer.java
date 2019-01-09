package redaktor.initialize;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.function.Function;

public class ViewInitializer {

    public static <T1, T2> TableColumn<T1, T2> createColumn(String columnName, String fieldName, int width) {
        TableColumn<T1, T2> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<T1, T2>(fieldName));

        return tableColumn;
    }

    public static <T> void addColumnsToTableView(TableView<T> tableView, TableColumn<T, ?> ... tableColumns) {
        tableView.getColumns().addAll(tableColumns);
    }

    public static <T> void setObservableListToTableView(TableView<T> tableView, ObservableList<T> observableList) {
        tableView.setItems(observableList);
    }


    public static <T> void initializeChoiceBox(ChoiceBox<T> choiceBox, Function<T, String> choiceBoxDisplayNameRetriever) {
        choiceBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T choiceBoxStoredType) {
                return choiceBoxDisplayNameRetriever.apply(choiceBoxStoredType);
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        });


    }
}
