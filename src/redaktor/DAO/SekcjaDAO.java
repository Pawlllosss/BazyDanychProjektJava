package redaktor.DAO;

import redaktor.connection.ConnectionHandler;
import redaktor.model.Sekcja;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SekcjaDAO implements DAO<Sekcja> {

    private static DAO<Sekcja> sekcjaDAO = new SekcjaDAO();
    private ConnectionHandler connectionHandler;

    public static DAO<Sekcja> getInstance() {
        return sekcjaDAO;
    }

    private SekcjaDAO() {
        connectionHandler = ConnectionHandler.getInstance();
    }
    @Override
    public Sekcja get(long id) {
        return null;
    }

    public Sekcja getByNazwa(String nazwa) {
        //TODO: check while adding if section with name already exists
        String sekcjaSelectByNazwaQuery = "SELECT * FROM redaktor.sekcja s WHERE s.nazwa = ?;";
        List<Sekcja> sections = new LinkedList<>();
        Sekcja sekcja = null;

        PreparedStatement selectByNazwaStatement = connectionHandler.prepareStatement(sekcjaSelectByNazwaQuery);
        try {
            selectByNazwaStatement.setString(1, nazwa);
            ResultSet resultSet = selectByNazwaStatement.executeQuery();
            sections = getSectionsFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!sections.isEmpty()) {
            sekcja = sections.get(0);
        }

        return sekcja;
    }

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

    }

    @Override
    public void delete(long id) {

    }

    private List<Sekcja> getSectionsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Sekcja> sections = new LinkedList<>();

        while(resultSet.next()) {
            long sekcjaId  = resultSet.getLong("sekcja_id");
            String nazwa = resultSet.getString("nazwa");
            
            Sekcja sekcja = new Sekcja(sekcjaId, nazwa);
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
