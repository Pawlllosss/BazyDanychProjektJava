package redaktor.controller.alert;

import javafx.scene.control.Alert;

public class WarningAlert {

    Alert alert;

    public WarningAlert(String contentText) {
        alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Błąd!");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
