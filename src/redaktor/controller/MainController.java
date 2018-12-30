package redaktor.controller;

//TODO: create tabs
//TODO: refresh redactor list automatically

import javafx.fxml.FXML;
import java.util.LinkedList;

public class MainController {

    //TODO: move it to some wrapper?
    private static LinkedList<ValueObjectController> valueObjectControllers;

//    @FXML
//    private RedaktorzyTabController redaktorzyTabController;
//    @FXML
//    private SekcjeTabController sekcjeTabController;

    //it's called after all childs FXML where processed
    @FXML
    private void initialize() {
        System.out.println("MainController initialized");

        for(ValueObjectController valueObjectController : valueObjectControllers) {
            valueObjectController.setItemsFromOtherControllers();
        }

        //perform queue
    }

    //it's called at the beginning
    public MainController() {
        valueObjectControllers = new LinkedList<>();
    }

    //TODO: in Java8 I could use interface with static method
    public static <T> void addValueObjectController(ValueObjectController<T> valueObjectController) {
        valueObjectControllers.add(valueObjectController);
    }
}
