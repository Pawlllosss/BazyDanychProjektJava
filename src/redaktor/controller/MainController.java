package redaktor.controller;

//TODO: create tabs
//TODO: refresh redactor list automatically

import javafx.fxml.FXML;
import java.util.LinkedList;

public class MainController {

    //TODO: move it to some wrapper?
    private static LinkedList<EntityController> entityControllers;


    //it's called after all childs FXML where processed
    @FXML
    private void initialize() {
        System.out.println("MainController initialized");
        performAfterInitializeActionOnChildControllers();
    }

    //it's called at the beginning
    public MainController() {
        entityControllers = new LinkedList<>();
    }

    //TODO: in Java8 I could use interface with static method
    public static <T> void addEntityController(EntityController<T> entityController) {
        entityControllers.add(entityController);
    }

    private void performAfterInitializeActionOnChildControllers() {

        for(EntityController entityController : entityControllers) {
            entityController.setItemsFromOtherControllers();
        }
    }
}
