package redaktor.DAO;

import redaktor.connection.ConnectionHandler;
import redaktor.model.Program;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProgramDAO implements DAO<Program> {

    private static ProgramDAO programDAO = new ProgramDAO();
    private ConnectionHandler connectionHandler;

    public static ProgramDAO getInstance() {
        return programDAO;
    }

    private ProgramDAO() {
        connectionHandler = ConnectionHandler.getInstance();
    }

    @Override
    public Program get(long id) {
        return null;
    }

    @Override
    public List<Program> getAll() {
        final String PROGRAM_SELECT_QUERY = "SELECT * FROM redaktor.program;";


        ResultSet resultSet = connectionHandler.executeSelectQuery(PROGRAM_SELECT_QUERY);
        List<Program> programy = null;

        try {
            programy = getProgramyFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return programy;
    }

    @Override
    public void save(Program program) {
        String programInsertQuery = "INSERT INTO redaktor.program(nazwa, opis, sekcja_id) VALUES (?, ?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(programInsertQuery);

        try {
            preparedStatement.setString(1, program.getNazwa());
            preparedStatement.setString(2, program.getOpis());
            preparedStatement.setLong(3, program.getSekcjaId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {

    }

    private List<Program> getProgramyFromResultSet(ResultSet resultSet) throws SQLException {
        List<Program> sections = new LinkedList<>();

        while(resultSet.next()) {
            long programId  = resultSet.getLong("program_id");
            String nazwa = resultSet.getString("nazwa");
            String opis = resultSet.getString("opis");
            long sekcjaId  = resultSet.getLong("sekcja_id");

            Program program = new Program(programId, nazwa, opis, sekcjaId);
            sections.add(program);
        }

        return sections;
    }
}
