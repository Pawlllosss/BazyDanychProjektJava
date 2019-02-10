package redaktor.controller.crud;

import redaktor.DAO.DAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormWithValidation;
import redaktor.controller.observable.ObservableEntityNoUpdateArgumentsListWrapper;

public class EntityAdder {
    public static <T> void tryToAddEntity(FormWithValidation<T> entityForm, DAO<T> dao, ObservableEntityNoUpdateArgumentsListWrapper<T> observableEntityListWrapper) {
        if(entityForm.isFormCorrectlyFilled()) {
            T entity = entityForm.readForm();
            dao.save(entity);
            observableEntityListWrapper.updateObservableList();
        }
        else {
            WarningAlert warningAlert = new WarningAlert("Niepoprawnie wypełnione pola!");
            warningAlert.showAndWait();
        }
    }
}
