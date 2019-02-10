package redaktor.DAO;

import redaktor.DAO.statement.PreparedStatementSetter;
import redaktor.DAO.update.RedaktorUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Redaktor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Redaktor> get(long id) {
        String sekcjaSelectBySekcjaIdQuery = "SELECT * FROM redaktor.redaktor r WHERE r.redaktor_id = ?;";

        List<Redaktor> redaktors = new LinkedList<>();
        Redaktor redaktor = null;

        PreparedStatement selectByRedaktorIdStatement = connectionHandler.prepareStatement(sekcjaSelectBySekcjaIdQuery);
        try {
            selectByRedaktorIdStatement.setLong(1, id);
            ResultSet resultSet = selectByRedaktorIdStatement.executeQuery();
            redaktors = getRedactorsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!redaktors.isEmpty()) {
            redaktor = redaktors.get(0);
        }

        return Optional.ofNullable(redaktor);
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
            PreparedStatementSetter.setForeignKey(preparedStatement, 3, redaktor.getSekcjaId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Redaktor originalEntity, Redaktor editedEntity) {
        RedaktorUpdateQueryBuilder redaktorUpdateQueryBuilder = new RedaktorUpdateQueryBuilder();
        final String UPDATE_QUERY = redaktorUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String redaktorDeleteQuery = "DELETE FROM redaktor.redaktor WHERE redaktor_id = ?";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(redaktorDeleteQuery);

        try {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
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
