package redaktor.controller.choicebox;

import javafx.scene.control.ChoiceBox;

final public class ChoiceBoxHelper {

    public static<T> void selectItemInChoiceBoxt(ChoiceBox<T> choiceBox, T item) {
        choiceBox.setValue(item);
    }
}
