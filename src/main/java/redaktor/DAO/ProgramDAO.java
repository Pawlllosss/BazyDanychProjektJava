package redaktor.DAO;

import redaktor.DAO.query.DeleteQueryExecutor;
import redaktor.DAO.update.ProgramUpdateQueryBuilder;
import redaktor.connection.ConnectionHandler;
import redaktor.model.program.Program;
import redaktor.model.program.view.ProgramPrzypisanyRedaktor;
import redaktor.model.program.view.ProgramRedaktorCount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Program> get(long id) {
        return Optional.ofNullable(null);
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
    public void update(Program originalEntity, Program editedEntity) {
        ProgramUpdateQueryBuilder programUpdateQueryBuilder = new ProgramUpdateQueryBuilder();
        final String UPDATE_QUERY = programUpdateQueryBuilder.buildUpdateQuery(originalEntity, editedEntity);

        connectionHandler.executeUpdateQuery(UPDATE_QUERY);
    }

    @Override
    public void delete(long id) {
        String programDeleteQuery = "DELETE FROM redaktor.program WHERE program_id = ?";

        DeleteQueryExecutor deleteQueryExecutor = new DeleteQueryExecutor(programDeleteQuery, id);
        deleteQueryExecutor.executeDeleteQuery();
    }

    public List<ProgramRedaktorCount> getProgramRedaktorCount() {
        final String PROGRAM_REDAKTOR_COUNT_SELECT_QUERY = "SELECT * FROM redaktor.program_redaktor_count;";

        ResultSet resultSet = connectionHandler.executeSelectQuery(PROGRAM_REDAKTOR_COUNT_SELECT_QUERY);
        List<ProgramRedaktorCount> programRedaktorCounts = null;

        try {
            programRedaktorCounts = getProgramRedaktorCountFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return programRedaktorCounts;
    }

    public List<ProgramPrzypisanyRedaktor> getProgramPrzypisanyRedaktor() {
        final String PROGRAM_REDAKTOR_COUNT_SELECT_QUERY = "SELECT * FROM redaktor.program_przypisany_redaktor;";

        ResultSet resultSet = connectionHandler.executeSelectQuery(PROGRAM_REDAKTOR_COUNT_SELECT_QUERY);
        List<ProgramPrzypisanyRedaktor> programPrzypisanyRedaktors = null;

        try {
            programPrzypisanyRedaktors = getProgramPrzypisanyRedaktorFromResultSet(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return programPrzypisanyRedaktors;
    }

    public void saveRedaktorProgramRelation(long redaktorId, long progamId) {
        String redaktorProgramRelationInsertQuery = "INSERT INTO redaktor.redaktor_program(redaktor_id, program_id) VALUES (?, ?);";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(redaktorProgramRelationInsertQuery);

        try {
            preparedStatement.setLong(1, redaktorId);
            preparedStatement.setLong(2, progamId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRedaktorProgramRelation(long programId, long redaktorId) {
        String redaktorProgramRelationDeleteQuery = "DELETE FROM redaktor.redaktor_program WHERE program_id = ? AND redaktor_id = ?";

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(redaktorProgramRelationDeleteQuery);

        try {
            preparedStatement.setLong(1, programId);
            preparedStatement.setLong(2, redaktorId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Program> getProgramyFromResultSet(ResultSet resultSet) throws SQLException {
        List<Program> programs = new LinkedList<>();

        while(resultSet.next()) {
            long programId  = resultSet.getLong("program_id");
            String nazwa = resultSet.getString("nazwa");
            String opis = resultSet.getString("opis");
            long sekcjaId  = resultSet.getLong("sekcja_id");

            Program program = new Program(programId, nazwa, opis, sekcjaId);
            programs.add(program);
        }

        return programs;
    }

    private List<ProgramRedaktorCount> getProgramRedaktorCountFromResultSet(ResultSet resultSet) throws SQLException {
        List<ProgramRedaktorCount> programRedaktorCounts = new LinkedList<>();

        while(resultSet.next()) {
            Long programId  = resultSet.getLong("program_id");
            String nazwa = resultSet.getString("nazwa");
            long redaktorCount = resultSet.getLong("redaktor_count");

            ProgramRedaktorCount programRedaktorCount = new ProgramRedaktorCount(programId, nazwa, redaktorCount);
            programRedaktorCounts.add(programRedaktorCount);
        }

        return programRedaktorCounts;
    }

    private List<ProgramPrzypisanyRedaktor> getProgramPrzypisanyRedaktorFromResultSet(ResultSet resultSet) throws SQLException {
        List<ProgramPrzypisanyRedaktor> progamPrzypisanyRedaktors = new LinkedList<>();

        while(resultSet.next()) {
            Long programId  = resultSet.getLong("program_id");
            String prgramNazwa = resultSet.getString("program_nazwa");
            String imieNazwisko = resultSet.getString("imie_nazwisko");
            long redaktorId = resultSet.getLong("redaktor_id");

            ProgramPrzypisanyRedaktor programPrzypisanyRedaktor = new ProgramPrzypisanyRedaktor(programId, redaktorId, prgramNazwa, imieNazwisko);
            progamPrzypisanyRedaktors.add(programPrzypisanyRedaktor);
        }

        return progamPrzypisanyRedaktors;
    }
}
