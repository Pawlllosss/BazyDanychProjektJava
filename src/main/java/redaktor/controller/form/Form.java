package redaktor.controller.form;

public interface Form<T> {
    T readForm();
    void loadValuesIntoForm(T entity);
}
