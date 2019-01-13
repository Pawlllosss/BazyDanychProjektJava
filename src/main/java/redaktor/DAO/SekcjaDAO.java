package redaktor.DAO;

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

    private static SekcjaDAO sekcjaDAO = new SekcjaDAO();
    private ConnectionHandler connectionHandler;

    public static SekcjaDAO getInstance() {
        return sekcjaDAO;
    }

    private SekcjaDAO() {
        connectionHandler = ConnectionHandler.getInstance();
    }
    @Override
    public Optional<Sekcja> get(long id) {
        String sekcjaSelectBySekcjaIdQuery = "SELECT * FROM redaktor.sekcja s WHERE s.sekcja_id = ?;";

        List<Sekcja> sections = new LinkedList<>();
        Sekcja sekcja = null;

        PreparedStatement selectBySekcjaIdStatement = connectionHandler.prepareStatement(sekcjaSelectBySekcjaIdQuery);
        try {
            selectBySekcjaIdStatement.setLong(1, id);
            ResultSet resultSet = selectBySekcjaIdStatement.executeQuery();
            sections = getSectionsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!sections.isEmpty()) {
            sekcja = sections.get(0);
        }

        return Optional.ofNullable(sekcja);
    }

//    public Sekcja getByNazwa(String nazwa) {
//        //TODO: check while adding if section with name already exists
//        String sekcjaSelectByNazwaQuery = "SELECT * FROM redaktor.sekcja s WHERE s.nazwa = ?;";
//        List<Sekcja> sections = new LinkedList<>();
//        Sekcja sekcja = null;
//
//        PreparedStatement selectByNazwaStatement = connectionHandler.prepareStatement(sekcjaSelectByNazwaQuery);
//        try {
//            selectByNazwaStatement.setString(1, nazwa);
//            ResultSet resultSet = selectByNazwaStatement.executeQuery();
//            sections = getSectionsFromResultSet(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if(!sections.isEmpty()) {
//            sekcja = sections.get(0);
//        }
//
//        return sekcja;
//    }

    @Override
    public List<Sekcja> getAll() {
        final String SEKCJA_SELECT_QUERY = "SELECT * FROM redaktor.sekcja;";

        ResultSet resultSet = connectionHandler.executeSelectQuery(SEKCJA_SELECT_QUERY);
        List<Sekcja> sections = null;

        try {
            sections = getSectionsFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return sections;
    }

    public List<String> getAllNazwa() {
        final String SEKCJA_NAZWA_SELECT_QUERY = "select s.nazwa from redaktor.sekcja s;";

        ResultSet resultSet = connectionHandler.executeSelectQuery(SEKCJA_NAZWA_SELECT_QUERY);
        List<String> sectionsName = null;

        try {
            sectionsName = getSectionsNameFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return sectionsName;

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

        System.out.println(UPDATE_QUERY);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String sekcjaDeleteQuery = "DELETE FROM redaktor.sekcja WHERE sekcja_id = ?";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(sekcjaDeleteQuery);

        try {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Sekcja> getSectionsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Sekcja> sections = new LinkedList<>();

        while(resultSet.next()) {
            long sekcjaId  = resultSet.getLong("sekcja_id");
            String nazwa = resultSet.getString("nazwa");
            long szefId  = resultSet.getLong("redaktor_id");

            Sekcja sekcja = new Sekcja(sekcjaId, nazwa, szefId);
            sections.add(sekcja);
        }

        return sections;
    }

    private List<String> getSectionsNameFromResultSet(ResultSet resultSet) throws SQLException {
        List<String> sekcjaNazwa = new LinkedList<>();

        while(resultSet.next()) {
            String nazwa = resultSet.getString("nazwa");
            sekcjaNazwa.add(nazwa);
        }

        return sekcjaNazwa;
    }
}
