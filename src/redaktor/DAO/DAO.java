package redaktor.DAO;

import java.util.List;
import java.util.Map;

public interface DAO<T> {
    //TODO: in java8 I could use optionals...
    T get(long id);
    List<T> getAll();
    void save(T t);
    void update(T originalValueObject, T editedValueObject);
    void delete(long id);
}
