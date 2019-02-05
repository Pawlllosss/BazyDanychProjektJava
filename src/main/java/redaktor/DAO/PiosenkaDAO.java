package redaktor.DAO;

import redaktor.DAO.delete.DeleteQueryExecutor;
import redaktor.DAO.get.GetExecutor;
import redaktor.DAO.update.PiosenkaUpdateQueryBuilder;
import redaktor.DAO.update.StudioUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Piosenka;
import redaktor.model.Studio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PiosenkaDAO implements DAO<Piosenka> {
    private final String TABLE_NAME = "piosenka";
    private final String SCHEMA_NAME = "redaktor";
    private final String ID_FIELD = "piosenka_id";
    private static PiosenkaDAO piosenkaDAO = new PiosenkaDAO();

    private ConnectionHandler connectionHandler;
    private GetExecutor<Piosenka> getExecutor;

    public static PiosenkaDAO getInstance() {
        return piosenkaDAO;
    }

    private PiosenkaDAO() {
        connectionHandler = ConnectionHandler.getInstance();
        getExecutor = new GetExecutor<>(SCHEMA_NAME, TABLE_NAME, ID_FIELD, this::getPiosenkasFromResultSet);
    }

    @Override
    public Optional<Piosenka> get(long id) {
        Piosenka piosenka = getExecutor.execute(id);
        return Optional.ofNullable(piosenka);
    }

    @Override
    public List<Piosenka> getAll() {
        List<Piosenka> piosenkas = getExecutor.executeAll();
        return piosenkas;
    }

    @Override
    public void save(Piosenka piosenka) {
        String studioInsertQuery = "INSERT INTO redaktor.piosenka(tytul, wykonawca) " +
                "VALUES (?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(studioInsertQuery);

        try {
            preparedStatement.setString(1, piosenka.getTytul());
            preparedStatement.setString(2, piosenka.getWykonawca());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Piosenka originalEntity, Piosenka editedEntity) {
        PiosenkaUpdateQueryBuilder piosenkaUpdateQueryBuilder = new PiosenkaUpdateQueryBuilder(SCHEMA_NAME, TABLE_NAME, ID_FIELD);
        final String UPDATE_QUERY = piosenkaUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String piosenkaDeleteQuery = "DELETE FROM redaktor." + TABLE_NAME + " WHERE piosenka_id = ?";
        DeleteQueryExecutor deleteQueryExecutor = new DeleteQueryExecutor(piosenkaDeleteQuery, id);
        deleteQueryExecutor.executeDeleteQuery();
    }

    private List<Piosenka> getPiosenkasFromResultSet(ResultSet resultSet) throws SQLException {
        List<Piosenka> piosenkas = new LinkedList<>();

        while(resultSet.next()) {
            Long piosenkaId  = resultSet.getLong("piosenka_id");
            String tytul = resultSet.getString("tytul");
            String wykonawca = resultSet.getString("wykonawca");

            Piosenka piosenka = new Piosenka(piosenkaId, tytul, wykonawca);
            piosenkas.add(piosenka);
        }

        return piosenkas;
    }
}
