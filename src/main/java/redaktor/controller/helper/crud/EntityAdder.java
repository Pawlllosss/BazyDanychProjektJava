package redaktor.controller.helper.crud;

import redaktor.DAO.DAO;
import redaktor.controller.alert.WarningAlert;
import redaktor.controller.form.FormWithValidation;
import redaktor.controller.helper.observable.ObservableEntityListWrapper;

public class EntityAdder {
    public static <T> void tryToAddEntity(FormWithValidation<T> entityForm, DAO<T> dao, ObservableEntityListWrapper<T> observableEntityListWrapper) {
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
