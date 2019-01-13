package redaktor.controller.form;

public interface FormWithValidation<T> extends Form<T> {
    boolean isFormDifferentFromEntity(T entity);
    boolean isFormCorrectlyFilled();
}
