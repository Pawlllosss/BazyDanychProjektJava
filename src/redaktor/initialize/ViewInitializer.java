package redaktor.initialize;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

public class ViewInitializer {

    //TODO: in Java8 I could use functional interface...
    public static <T> void initializeChoiceBox(ChoiceBox<T> choiceBox, final DisplayNameRetriever displayNameRetriever) {
        choiceBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T choiceBoxStoredType) {
                return displayNameRetriever.getName(choiceBoxStoredType);
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        });
    }

    public static <T1, T2> TableColumn<T1, T2> createColumn(String columnName, String fieldName, int width) {
        TableColumn<T1, T2> tableColumn = new TableColumn<>(columnName);
        tableColumn.setMinWidth(width);
        tableColumn.setCellValueFactory(new PropertyValueFactory<T1, T2>(fieldName));

        return tableColumn;
    }

    public static <T> void addColumnsToTableView(TableView<T> tableView, TableColumn<T, ?> ... tableColumns) {
        tableView.getColumns().addAll(tableColumns);
    }

}
