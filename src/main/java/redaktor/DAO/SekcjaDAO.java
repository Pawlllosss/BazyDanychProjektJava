package redaktor.DAO;

import redaktor.DAO.delete.DeleteQueryExecutor;
import redaktor.DAO.get.GetExecutor;
import redaktor.DAO.statement.PreparedStatementSetter;
import redaktor.DAO.update.SekcjaUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Sekcja;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SekcjaDAO implements DAO<Sekcja> {
    private final String TABLE_NAME = "sekcja";
    private final String SCHEMA_NAME = "redaktor";
    private final String ID_FIELD = "sekcja_id";

    private static SekcjaDAO sekcjaDAO = new SekcjaDAO();
    private ConnectionHandler connectionHandler;
    private GetExecutor<Sekcja> getExecutor;

    public static SekcjaDAO getInstance() {
        return sekcjaDAO;
    }

    private SekcjaDAO() {
        connectionHandler = ConnectionHandler.getInstance();
        getExecutor = new GetExecutor<>(SCHEMA_NAME, TABLE_NAME, ID_FIELD, this::getSekcjasFromResultSet);
    }

    @Override
    public Optional<Sekcja> get(long id) {
        Sekcja sekcja = getExecutor.execute(id);
        return Optional.ofNullable(sekcja);
    }

    @Override
    public List<Sekcja> getAll() {
        List<Sekcja> sekcjas = getExecutor.executeAll();
        return sekcjas;
    }

    @Override
    public void save(Sekcja sekcja) {
        String sekcjaInsertQuery = "INSERT INTO redaktor.sekcja(nazwa, redaktor_id) VALUES (?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(sekcjaInsertQuery);

        try {
            preparedStatement.setString(1, sekcja.getNazwa());
            Long szefId = sekcja.getSzefId();
            PreparedStatementSetter.setForeignKey(preparedStatement, 2, szefId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Sekcja originalEntity, Sekcja editedEntity) {
        SekcjaUpdateQueryBuilder sekcjaUpdateQueryBuilder = new SekcjaUpdateQueryBuilder();
        final String UPDATE_QUERY = sekcjaUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String sekcjaDeleteQuery = "DELETE FROM redaktor.sekcja WHERE sekcja_id = ?";
        DeleteQueryExecutor deleteQueryExecutor = new DeleteQueryExecutor(sekcjaDeleteQuery, id);
        deleteQueryExecutor.executeDeleteQuery();
    }

    private List<Sekcja> getSekcjasFromResultSet(ResultSet resultSet) throws SQLException {
        List<Sekcja> sections = new LinkedList<>();

        while (resultSet.next()) {
            long sekcjaId = resultSet.getLong("sekcja_id");
            String nazwa = resultSet.getString("nazwa");
            long szefId = resultSet.getLong("redaktor_id");

            Sekcja sekcja = new Sekcja(sekcjaId, nazwa, szefId);
            sections.add(sekcja);
        }

        return sections;
    }
}
