package redaktor.DAO;

import redaktor.DAO.delete.DeleteQueryExecutor;
import redaktor.DAO.get.GetExecutor;
import redaktor.DAO.statement.PreparedStatementSetter;
import redaktor.DAO.update.AudycjaUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Audycja;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AudycjaDAO implements DAO<Audycja> {
    private final String TABLE_NAME = "audycja";
    private final String SCHEMA_NAME = "redaktor";
    private final String ID_FIELD = "audycja_id";

    private static AudycjaDAO studioDAO = new AudycjaDAO();
    private ConnectionHandler connectionHandler;
    private GetExecutor<Audycja> getExecutor;

    public static AudycjaDAO getInstance() {
        return studioDAO;
    }

    private AudycjaDAO() {
        connectionHandler = ConnectionHandler.getInstance();
        getExecutor = new GetExecutor<>(SCHEMA_NAME, TABLE_NAME, ID_FIELD, this::getAudycjasFromResultSet);
    }

    @Override
    public Optional<Audycja> get(long id) {
        Audycja audycja = getExecutor.execute(id);
        return Optional.ofNullable(audycja);
    }

    @Override
    public List<Audycja> getAll() {
        List<Audycja> audycjas = getExecutor.executeAll();
        return audycjas;
    }

    @Override
    public void save(Audycja audycja) {
        String audycjaInsertQuery = "INSERT INTO redaktor.audycja(data_poczatek, data_koniec, program_id, studio_id) " +
                "VALUES (?, ?, ?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(audycjaInsertQuery);

        try {
            preparedStatement.setTimestamp(1, audycja.getDataPoczatek());
            preparedStatement.setTimestamp(2, audycja.getDataKoniec());
            PreparedStatementSetter.setForeignKey(preparedStatement, 3, audycja.getProgramId());
            PreparedStatementSetter.setForeignKey(preparedStatement, 4, audycja.getStudioId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Audycja originalEntity, Audycja editedEntity) {
        AudycjaUpdateQueryBuilder audycjaUpdateQueryBuilder = new AudycjaUpdateQueryBuilder();
        final String UPDATE_QUERY = audycjaUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        System.out.println(UPDATE_QUERY);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String audycjaDeleteQuery = "DELETE FROM redaktor.audycja WHERE audycja_id = ?";
        DeleteQueryExecutor deleteQueryExecutor = new DeleteQueryExecutor(audycjaDeleteQuery, id);
        deleteQueryExecutor.executeDeleteQuery();
    }

    public List<Audycja> getAudycjasInDay(Date date) {
        final String AUDYCJAS_IN_DAY_QUERY = "SELECT * FROM get_audycjas_in_day(?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(AUDYCJAS_IN_DAY_QUERY);
        List<Audycja> audycjasInDay = null;

        try {
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            audycjasInDay = getAudycjasFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return audycjasInDay;
    }

    private List<Audycja> getAudycjasFromResultSet(ResultSet resultSet) throws SQLException {
        List<Audycja> audycjas = new LinkedList<>();

        while(resultSet.next()) {
            Long audycjaId  = resultSet.getLong("audycja_id");
            Timestamp dataPoczatek = resultSet.getTimestamp("data_poczatek");
            Timestamp dataKoniec = resultSet.getTimestamp("data_koniec");
            Long programId = resultSet.getLong("program_id");
            Long studioId = resultSet.getLong("studio_id");

            Audycja audycja = new Audycja(audycjaId, dataPoczatek, dataKoniec, programId, studioId);
            audycjas.add(audycja);
        }

        return audycjas;
    }
}
