package redaktor.DAO;

import redaktor.DAO.delete.DeleteQueryExecutor;
import redaktor.DAO.get.GetExecutor;
import redaktor.DAO.update.StudioUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Studio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class StudioDAO implements DAO<Studio> {
    private final String TABLE_NAME = "studio";
    private final String SCHEMA_NAME = "redaktor";
    private final String ID_FIELD = "studio_id";

    private static StudioDAO studioDAO = new StudioDAO();
    private ConnectionHandler connectionHandler;
    private GetExecutor<Studio> getExecutor;

    public static StudioDAO getInstance() {
        return studioDAO;
    }

    private StudioDAO() {
        connectionHandler = ConnectionHandler.getInstance();
        getExecutor = new GetExecutor<>(SCHEMA_NAME, TABLE_NAME, ID_FIELD, this::getStudiosFromResultSet);
    }

    @Override
    public Optional<Studio> get(long id) {
        Studio studio = getExecutor.execute(id);
        return Optional.ofNullable(studio);
    }

    @Override
    public List<Studio> getAll() {
        List<Studio> studios = getExecutor.executeAll();
        return studios;
    }

    @Override
    public void save(Studio studio) {
        String studioInsertQuery = "INSERT INTO redaktor.studio(nazwa, nr_pokoju) " +
                "VALUES (?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(studioInsertQuery);

        try {
            preparedStatement.setString(1, studio.getNazwa());
            preparedStatement.setInt(2, studio.getNrPokoju());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Studio originalEntity, Studio editedEntity) {
        StudioUpdateQueryBuilder studioUpdateQueryBuilder = new StudioUpdateQueryBuilder();
        final String UPDATE_QUERY = studioUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String redaktorDeleteQuery = "DELETE FROM redaktor.studio WHERE studio_id = ?";
        DeleteQueryExecutor deleteQueryExecutor = new DeleteQueryExecutor(redaktorDeleteQuery, id);
        deleteQueryExecutor.executeDeleteQuery();
    }

    private List<Studio> getStudiosFromResultSet(ResultSet resultSet) throws SQLException {
        List<Studio> studios = new LinkedList<>();

        while(resultSet.next()) {
            Long studioId  = resultSet.getLong("studio_id");
            String nazwa = resultSet.getString("nazwa");
            Integer nrPokoju = resultSet.getInt("nr_pokoju");

            Studio studio = new Studio(studioId, nazwa, nrPokoju);
            studios.add(studio);
        }

        return studios;
    }
}
