package redaktor.controller.form.DAO.update;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redaktor.DAO.update.RedaktorUpdateQueryBuilder;
import redaktor.model.Redaktor;

public class RedaktorUpdateQueryBuilderTest {

    private RedaktorUpdateQueryBuilder redaktorUpdateQueryBuilder;

    @BeforeEach
    void setup() {
        redaktorUpdateQueryBuilder = new RedaktorUpdateQueryBuilder();
    }

    @Test
    void shouldReturnCorrectQueryWhenAllFieldsInEditedObjectExceptIdAreNull() {
        Long redaktorId = 1L;
        Redaktor originalRedaktor = new Redaktor(redaktorId, "Stefan", "Ącki", 1L);
        Redaktor editedRedaktor = new Redaktor(redaktorId, null, null, null);

        String updateQuery = redaktorUpdateQueryBuilder.buildUpdateQuery(originalRedaktor, editedRedaktor);
        String expectedUpdateQuery = "UPDATE redaktor.redaktor SET imie = 'null' ," +
                "nazwisko = 'null' ,sekcja_id = 'null' WHERE redaktor_id = "
                + redaktorId + ";";

        assertEquals(expectedUpdateQuery, updateQuery);
    }

    @Test
    void shouldReturnCorrectQueryWhenOnlyOneParameterIsModified() {
        Long redaktorId = 2000L;
        String originalRedaktorImie = "Ędward";
        String redaktorNazwisko = "Ącki";
        Long redaktorSekcjaId = 1L;
        String editedRedaktorImie = "Bękart";

        Redaktor originalRedaktor = new Redaktor(redaktorId, originalRedaktorImie, redaktorNazwisko, redaktorSekcjaId);
        Redaktor editedRedaktor = new Redaktor(redaktorId, editedRedaktorImie, redaktorNazwisko, redaktorSekcjaId);

        String updateQuery = redaktorUpdateQueryBuilder.buildUpdateQuery(originalRedaktor, editedRedaktor);
        String expectedUpdateQuery = "UPDATE redaktor.redaktor SET imie = '" + editedRedaktorImie
                + "' WHERE redaktor_id = " + redaktorId + ";";

        assertEquals(expectedUpdateQuery, updateQuery);
    }
}
