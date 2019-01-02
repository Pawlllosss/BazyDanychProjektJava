package redaktor.controller.helper;

import javafx.scene.control.ChoiceBox;

public class ChoiceBoxHelper {

    public static<T> void selectItemInChoiceBoxt(ChoiceBox<T> choiceBox, T item) {
        choiceBox.setValue(item);
    }
}
