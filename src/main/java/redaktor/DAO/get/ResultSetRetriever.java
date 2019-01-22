package redaktor.DAO.get;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ResultSetRetriever<T> {
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
