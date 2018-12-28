package redaktor.DAO;

import redaktor.connection.ConnectionHandler;
import redaktor.model.Redaktor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RedaktorDAO implements DAO<Redaktor> {

    private static RedaktorDAO redactorQueryExecutor = new RedaktorDAO();
    private ConnectionHandler connectionHandler;

    public static RedaktorDAO getInstance() {
        return redactorQueryExecutor;
    }

    private RedaktorDAO() {
        connectionHandler = ConnectionHandler.getInstance();
    }


    @Override
    public Redaktor get(long id) {
        return null;
    }

    @Override
    public List<Redaktor> getAll() {
        final String REDACTOR_QUERY = "select r.redaktor_id, r.imie, r.nazwisko, s.nazwa from redaktor.redaktor r\n" +
                "join redaktor.sekcja s USING(sekcja_id);";

        ResultSet resultSet = connectionHandler.executeSelectQuery(REDACTOR_QUERY);
        List<Redaktor> redactors = new LinkedList<>();

        try {
            redactors = getRedactorsFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return redactors;
    }

    @Override
    public void save(Redaktor redaktor) {

    }

    @Override
    public void delete(long id) {

    }

    private List<Redaktor> getRedactorsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Redaktor> redactors = new LinkedList<>();

        while(resultSet.next()) {
            long redaktorId  = resultSet.getLong("redaktor_id");
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            long sekcjaId = resultSet.getLong("sekcja_id");

            Redaktor redaktor = new Redaktor(redaktorId, imie, nazwisko, sekcjaId);
            redactors.add(redaktor);
        }

        return redactors;
    }
}
