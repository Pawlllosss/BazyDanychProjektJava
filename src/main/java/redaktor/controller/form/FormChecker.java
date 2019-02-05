package redaktor.controller.form;

import redaktor.controller.alert.WarningAlert;

public class FormChecker {

    public static <T> boolean checkIfFormSuitableForEditAndDisplayWarningIfNot(FormWithValidation<T> formWithValidation, T entityToEdit) {

        if(!formWithValidation.isFormCorrectlyFilled()) {
            WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
            warningAlert.showAndWait();
            return false;
        }

        if(!formWithValidation.isFormDifferentFromEntity(entityToEdit)) {
            WarningAlert warningAlert = new WarningAlert("Nie zmodyfikowano żadnych pól!");
            warningAlert.showAndWait();
            return false;
        }

        return true;
    }
}
