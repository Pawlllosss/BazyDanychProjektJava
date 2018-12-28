package redaktor.DAO;

import java.util.List;

public interface DAO<T> {
    //TODO: in java8 I could use optionals...
    T get(long id);
    List<T> getAll();
    void save(T t);

    //TODO: think about arguments
    //void update(T t, parameterstoupdate)

    void delete(long id);
}
