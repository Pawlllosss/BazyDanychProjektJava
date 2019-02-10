package redaktor.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redaktor.connection.ConnectionHandler;
import redaktor.model.Redaktor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RedaktorDAOTests {
    private final static String REDAKTOR_TABLE = "redaktor.redaktor";
    private final static String SEKCJA_TABLE = "redaktor.sekcja";
    private RedaktorDAO redaktorDAO;


    @BeforeEach
    void init() {
        redaktorDAO = RedaktorDAO.getInstance();
    }

    @BeforeEach
    void prepareDatabase() {
        PreparedStatement truncateStatement = createTruncatePreparedStatement();

        try {
            truncateStatement.execute();
            createSampleSekcjas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldRedaktorBeSavedToDatabase() {
        Long redaktorId = 1L;
        String imie = "Jacek";
        String nazwisko = "Graniecki";
        Long sekcjaId = 1L;

        Redaktor redaktorToSave = new Redaktor(redaktorId, imie, nazwisko, sekcjaId);
        redaktorDAO.save(redaktorToSave);

        Optional<Redaktor> gettedRedaktorOptional = redaktorDAO.get(1L);
        assertNotNull(gettedRedaktorOptional.get());

        Redaktor gettedRedaktor = gettedRedaktorOptional.get();

        assertEquals(redaktorId, gettedRedaktor.getRedaktorId());
        assertEquals(imie, gettedRedaktor.getImie());
        assertEquals(nazwisko, gettedRedaktor.getNazwisko());
        assertEquals(sekcjaId, gettedRedaktor.getSekcjaId());
    }

    @Test
    void shouldReturnOptionalOfNullableWhenTryingToGetNotExistingEntity() {
        Optional<Redaktor> redaktor = redaktorDAO.get(1L);

        assertFalse(redaktor.isPresent());
    }

    private PreparedStatement createTruncatePreparedStatement() {
        ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
        final String TRUNCATE_STATEMENT = "TRUNCATE " + REDAKTOR_TABLE + ", " + SEKCJA_TABLE + " RESTART IDENTITY CASCADE";

        return connectionHandler.prepareStatement(TRUNCATE_STATEMENT);
    }

    private void createSampleSekcjas() {
        ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
        StringBuilder stringInsertQueryBuilder = new StringBuilder();

        stringInsertQueryBuilder.append("INSERT INTO redaktor.sekcja(sekcja_id, nazwa) VALUES ");
        stringInsertQueryBuilder.append("(1, 'News'), ");
        stringInsertQueryBuilder.append("(2, 'Kultura'), ");
        stringInsertQueryBuilder.append("(3, 'Sport');");

        PreparedStatement preparedStatement = connectionHandler.prepareStatement(stringInsertQueryBuilder.toString());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
