package redaktor.DAO;

import redaktor.connection.ConnectionHandler;
import redaktor.model.Redaktor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RedaktorDAO implements DAO<Redaktor> {

    private static RedaktorDAO redaktorDAO = new RedaktorDAO();
    private ConnectionHandler connectionHandler;

    public static RedaktorDAO getInstance() {
        return redaktorDAO;
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
        final String REDACTOR_SELECT_QUERY = "select r.redaktor_id, r.imie, r.nazwisko, r.sekcja_id from redaktor.redaktor r;";

        ResultSet resultSet = connectionHandler.executeSelectQuery(REDACTOR_SELECT_QUERY);
        List<Redaktor> redactors = null;

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
        String redactorInsertQuery = "INSERT INTO redaktor.redaktor(imie, nazwisko, sekcja_id) " +
                "VALUES (?, ?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(redactorInsertQuery);

        try {
            preparedStatement.setString(1, redaktor.getImie());
            preparedStatement.setString(2, redaktor.getNazwisko());
            preparedStatement.setLong(3, redaktor.getSekcjaId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
