package redaktor.controller.form;

public interface FormWithValidation<T> extends Form<T> {
    boolean isFormDiffersFromEntity(T entity);
    boolean isFormCorrectlyFilled();
}
