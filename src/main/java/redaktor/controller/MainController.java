package redaktor.controller;

import javafx.fxml.FXML;

import java.util.LinkedList;

public class MainController {

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

    public static <T> void addEntityController(EntityController<T> entityController) {
        entityControllers.add(entityController);
    }

    private void performAfterInitializeActionOnChildControllers() {

        for(EntityController entityController : entityControllers) {
            entityController.setItemsFromOtherControllers();
        }
    }
}
